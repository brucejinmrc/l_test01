package com.mrc.xmlfile.appspecs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mrc.xmlfile.appspecs.Application.Calculations.Calculation;
import com.mrc.xmlfile.appspecs.Application.Extobjects.Extobject;
import com.mrc.xmlfile.appspecs.Application.Fields.Field;
import com.mrc.xmlfile.appspecs.Application.Keys.Key;
import com.mrc.xmlfile.appspecs.Application.Links.Link;
import com.mrc.xmlfile.appspecs.Application.Selections.Selection;

/**
 * Compare app spec xml file 
 * @author bruce
 *
 */
public class AppSpecs {
	
	/** HTML overwrite options */
	public enum OverWriteOption {
		OVERWRITE_REQUIRED, NO_OVERWRITE_OR_ACTION_REQUIRED, NEED_PAINTER_ACTION
	};

	private Logger log = Logger.getLogger(this.getClass());
 
	/** app type: I/R/M/S */
	private String qtype;
	
	/** file location */
	private String fileloc;
	/** new file  */
	private File fileNew;
	/** new specs  */
	private Application specNew;

	/** old file */
	private File fileOld;
	/** old specs */
	private Application specOld;
	
	/** class file */
	private File classFile;
	/*****************************************************************
	 * Constructor.  
	 ****************************************************************/
	public AppSpecs(String fileloc, String oldfile, String newfile, String qtype) {
		this.qtype = qtype;
		
		String locold = fileloc + File.separator + oldfile;
		this.fileOld = new File(locold);
		this.specOld = (new AppSpecsFile(locold)).getAppSpecs();
				
		String locnew = fileloc + File.separator + newfile;	 
		this.fileNew = new File(locnew);
		this.specNew = (new AppSpecsFile(locnew)).getAppSpecs();
	
		String clsfile = StringUtils.replace(newfile, "_.xml", "s.class");
		this.classFile = new File(fileloc + File.separator + clsfile);
	}
 
	/*****************************************************************
	 * Return action required by the app compile 
	 ****************************************************************/
	public List<SpecStatus> specStatus() {
		
		List<SpecStatus> specStatus = new ArrayList<SpecStatus>();
		 
		/** OVERWRITE_REQUIRED */
		
		if (specOld == null || specNew == null) { //old html does not exist
			OverWriteOption opt = OverWriteOption.OVERWRITE_REQUIRED; 
			String msg = "Previously compiled HTML not found.";
			specStatus.add(new SpecStatus(msg,opt));
			return specStatus;
		}
		//long timediff = Math.abs(fileOld.lastModified() - fileNew.lastModified());
		//if (timediff < 1000) {
		//	return OverWriteOption.OVERWRITE; 
		//}
		
		if ( !specOld.getTemplate().equals(specNew.getTemplate()) ) { //tpl changed
			OverWriteOption opt = OverWriteOption.OVERWRITE_REQUIRED; 
			String msg = "Template has changed.";
			specStatus.add(new SpecStatus(msg,opt)); 
		} 
		
		if ( !specOld.getProgram().equals(specNew.getProgram()) ) { //pgm name changed
			OverWriteOption opt = OverWriteOption.OVERWRITE_REQUIRED; 
			String msg = "Program name has changed.";
			specStatus.add(new SpecStatus(msg,opt));
		}
		
		/** report/maintainer key added/removed */
		if (keyAdded() || keyRemoved()) {
			OverWriteOption opt = OverWriteOption.NO_OVERWRITE_OR_ACTION_REQUIRED; //I/S
			if (  qtype.equals("R") || qtype.equals("M")) { //R/M
				opt = OverWriteOption.OVERWRITE_REQUIRED; 
			}
			String msg = "Sequence keys have been added or removed.";
			specStatus.add(new SpecStatus(msg,opt));
		} 
		
		/** report/maintainer key reordered */
		if (  keyReordered()) {
			OverWriteOption opt = OverWriteOption.NO_OVERWRITE_OR_ACTION_REQUIRED; //I/S
			if (  qtype.equals("R") || qtype.equals("M")) { //R/M
				opt = OverWriteOption.OVERWRITE_REQUIRED; 
			}
			String msg = "Sequence keys have been reordered.";
			specStatus.add(new SpecStatus(msg,opt));
		} 
		
		/** report Add/Remove/Reorder Record Selection */
		if (  qtype.equals("R")  ) {  
			reportSelectionChange(specStatus) ;
		} 
		

		/** Add Maintainer radio, list box, check box */
		if (  qtype.equals("M")  && maintainerInput() ) {  
			OverWriteOption opt = OverWriteOption.OVERWRITE_REQUIRED; 
			String msg = "Added Maintainer radio, list box, check box.";
			specStatus.add(new SpecStatus(msg,opt));
		} 
		
		/**  Fields reordered */
		if ( fieldsReordered() ) {  
			OverWriteOption opt = OverWriteOption.OVERWRITE_REQUIRED; 
			String msg = "Fields have been reordered.";
			specStatus.add(new SpecStatus(msg,opt));
		} 
		
		
		/** NO_OVERWRITE_OR_ACTION_REQUIRED */
		

		/** Join type` */
		if (! specOld.getJointype().equals(specNew.getJointype()) ) {  
			OverWriteOption opt = OverWriteOption.NO_OVERWRITE_OR_ACTION_REQUIRED; 
			String msg = "Join type has changed.";
			specStatus.add(new SpecStatus(msg,opt));
		} 
		
		/** Retrievalkey added/removed */
		if ( qtype.equals("I") && specOld.getKeys().key.size() != specNew.getKeys().key.size() ) {  
			OverWriteOption opt = OverWriteOption.NO_OVERWRITE_OR_ACTION_REQUIRED; 
			String msg = "Sequence keys have beed added or removed.";
			specStatus.add(new SpecStatus(msg,opt));
		} 
		
		/** external object changed */
		if ( objectChanged() ) {  
			OverWriteOption opt = OverWriteOption.NO_OVERWRITE_OR_ACTION_REQUIRED; 
			String msg = "External object changed.";
			specStatus.add(new SpecStatus(msg,opt));
		} 
		
		/** calc changed & calc add/remove*/
		calcChanged(specStatus);
		
		
		/** NEED_PAINTER_ACTION */
		
		if ( !specOld.getTitle().equals(specNew.getTitle()) ) { //app desc changed
			OverWriteOption opt = OverWriteOption.NEED_PAINTER_ACTION; 
			String msg = "Application description has changed.";
			specStatus.add(new SpecStatus(msg,opt)); 
		} 
		
		if ( fieldAdded()  || fieldRemoved()) { //field add/remove
			OverWriteOption opt = OverWriteOption.NEED_PAINTER_ACTION; 
			String msg = "Field added or removed.";
			specStatus.add(new SpecStatus(msg,opt)); 
		} 
		
		if ( fieldDescChanged() ) { //field desc changed
			OverWriteOption opt = OverWriteOption.NEED_PAINTER_ACTION; 
			String msg = "Field description changed.";
			specStatus.add(new SpecStatus(msg,opt)); 
		} 
		
		if ( linksAddRemove() ) { //link add/remove	
			OverWriteOption opt = OverWriteOption.NEED_PAINTER_ACTION; 
			String msg = "Smartlinks added or removed.";
			specStatus.add(new SpecStatus(msg,opt)); 
		} 
		
		return specStatus;
	}
	
	/*****************************************************************
	 *  fields added 
	 ****************************************************************/
	public boolean fieldAdded() {
		
		List<Field> nfields = specNew.getFields().getField(); 
		for (Field fldn :  nfields) {
			Field ofld = oldField(fldn);
			if (ofld == null) {
				return true; //this new fld is added 
			}
		}
		 
		return false;
	}
	/*****************************************************************
	 *  fields removed
	 ****************************************************************/
	public boolean fieldRemoved() {
		
		List<Field> ofields = specOld.getFields().getField();
		for (Field fldo :  ofields) {
			Field fldn = newField(fldo);
			if (fldn == null) {
				return true; //this new fld is added 
			}
		}
		return false;
	}
	/*****************************************************************
	 *  fields reordered
	 ****************************************************************/
	public boolean fieldsReordered() {
		
		List<Field> ofieldsrmv = removeDeletedFields();
		List<Field> nfields = specNew.getFields().getField(); 
	 
		for (int i = 0; i< ofieldsrmv.size(); i++) {
			if (!ofieldsrmv.get(i).getName().equals(nfields.get(i).getName())) {
				return true;
			}
		}
		return false;
	}
	
	/*****************************************************************
	 *  Remove deleted from old fields
	 ****************************************************************/
	public List<Field> removeDeletedFields() {
		
		List<Field> fields = new ArrayList<Field>(); 

		List<Field> ofields = specOld.getFields().getField();
		for (Field fldo :  ofields) {
			Field fldn = newField(fldo);
			if (fldn == null) {
				continue;
			}
			fields.add(fldo);
		}
		
	 	return fields;
	}
	
	/*****************************************************************
	 *  Remove deleted from old keys
	 ****************************************************************/
	public List<Key> removeDeletedKeys() {
		
		List<Key> fields = new ArrayList<Key>(); 

		List<Key> ofields = specOld.getKeys().getKey();
		for (Key fldo :  ofields) {
			Key fldn = newKey(fldo);
			if (fldn == null) {
				continue;
			}
			fields.add(fldo);
		}
		
	 	return fields;
	}
	
	/*****************************************************************
	 *  Remove deleted from old selections
	 ****************************************************************/
	public List<Selection> removeDeletedSelections() {
		
		List<Selection> fields = new ArrayList<Selection>(); 

		List<Selection> ofields = specOld.getSelections().getSelection();
		for (Selection fldo :  ofields) {
			Selection fldn = newSelection(fldo);
			if (fldn == null) {
				continue;
			}
			fields.add(fldo);
		}
		
	 	return fields;
	}
	
	/*****************************************************************
	 *  Key added 
	 ****************************************************************/
	public boolean keyAdded() {
		
		List<Key> nfields = specNew.getKeys().getKey(); 
		for (Key fldn :  nfields) {
			Key ofld = oldKey(fldn);
			if (ofld == null) {
				return true; //this new fld is added 
			}
		}
		 
		return false;
	}
	/*****************************************************************
	 *  Key removed
	 ****************************************************************/
	public boolean keyRemoved() {
		
		List<Key> ofields = specOld.getKeys().getKey(); 
		for (Key fldo :  ofields) {
			Key fldn = newKey(fldo);
			if (fldn == null) {
				return true; //this new fld is added 
			}
		}
		return false;
	}
	/*****************************************************************
	 *  Key reordered
	 ****************************************************************/
	public boolean keyReordered() {
		
		List<Key> ofieldsrmv = removeDeletedKeys();
		List<Key> nfields = specNew.getKeys().getKey();
	 
		for (int i = 0; i< ofieldsrmv.size(); i++) {
			if (!ofieldsrmv.get(i).getField().equals(nfields.get(i).getField())) {
				return true;
			}
		}
		return false;
	}
	
	
	/*****************************************************************
	 *  links added or removed
	 ****************************************************************/
	public boolean linksAddRemove() {
		List<Link> olinks = specOld.links.getLink();
		List<Link> nlinks = specNew.links.getLink();
		if (olinks.size() != nlinks.size()) {
			return true;
		}
		return false;
	}
	
	/*****************************************************************
	 *  fields description changed
	 ****************************************************************/
	public boolean fieldDescChanged() {
		
		List<Field> nfields = specNew.getFields().getField(); 
		
		for (int i = 0; i< nfields.size(); i++) {
			String descn = nfields.get(i).getDescription();
			if (descn == null ) {
				continue; // 
			}
			
			Field ofld = oldField(nfields.get(i));
			if (ofld == null) {
				continue; //this is added fld, skip
			}
			
			String desco = ofld.getDescription();
			if (desco != null && !desco.equals(descn)) {
				return true;//this fld desc changed  
			}
			
		}
	
		return false;
	}
	
	/*****************************************************************
	 *  External object changed
	 ****************************************************************/
	public boolean objectChanged() {
		List<Extobject> objsOld =  specOld.extobjects.getExtobject();
		List<Extobject> objsNew =  specNew.extobjects.getExtobject();
		
		if ( objsOld.size() != objsNew.size() ) {  
			return true;  //added/removed
		} 
		
		for (int i = 0; i< objsOld.size(); i++) {
			Extobject objo = objsOld.get(i);
			Extobject objn = objsNew.get(i);
			if (!objo.getName().equals(objn.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	/*****************************************************************
	 *  calc changed
	 ****************************************************************/
	public boolean calcChanged(List<SpecStatus> specStatus) {
		List<Calculation> olds =  specOld.calculations.getCalculation();
		List<Calculation> news =  specNew.calculations.getCalculation();
		
		if ( olds.size() != news.size() ) {  
			OverWriteOption opt = OverWriteOption.NEED_PAINTER_ACTION; 
			String msg = "Calculation added or removed.";
			specStatus.add(new SpecStatus(msg,opt));
			return true;  //added/removed
		} 
		
		
		for (int i = 0; i< olds.size(); i++) {
			Calculation objo = olds.get(i);
			Calculation objn = news.get(i);
			if (    !objo.getName().equals(objn.getName())  || 
					!objo.getExpression().equals(objn.getExpression()) ||
					!objo.getDescription().equals(objn.getDescription()) ||
					 objo.getLength() != objn.getLength() ||
					 objo.getDecimal() != objn.getDecimal() ) {
				OverWriteOption opt = OverWriteOption.NO_OVERWRITE_OR_ACTION_REQUIRED; 
				String msg = "Calculation changed.";
				specStatus.add(new SpecStatus(msg,opt));
				return true;
			}
		}
		
		return false;
	}
	
	/*****************************************************************
	 *  Maintainer has added radio/checkbox
	 ****************************************************************/
	private String[] relsa = {"RD", "CK", "LS"};
	private List<String> rels = Arrays.asList(relsa);
	public boolean maintainerInput() {
		
		List<Field> nfields = specNew.getFields().getField(); 
		
		for (int i = 0; i< nfields.size(); i++) {
			String reln = nfields.get(i).getRelation();
			if (reln == null || !rels.contains(reln)) {
				continue; //this fld has no RD,CK,LS
			}
			
			Field ofld = oldField(nfields.get(i));
			if (ofld == null) {
				continue; //this is added fld, skip
			}
			
			String relo = ofld.getRelation();
			if (relo != null && !relo.equals(reln)) {
				return true;//this fld changed RD,CK,LS
			}
			
		}
		 
		return false;
	}
	
	/*****************************************************************
	 *  return the old field 
	 ****************************************************************/
	public Field oldField(Field newfld) {
		
		String newname = newfld.getName();		
		Field ofld = null;
		List<Field> ofields = specOld.getFields().getField();
		
		for (int i = 0; i< ofields.size(); i++) {
			String oldname = ofields.get(i).getName();
			if (oldname != null && oldname.equals(newname)) {
				ofld = ofields.get(i);
				break;
			}
		}
		 
		return ofld;
	}
	/*****************************************************************
	 *  return the new field 
	 ****************************************************************/
	public Field newField(Field oldfld) {
		
		String oldname = oldfld.getName();		
		Field ofld = null;
		List<Field> nfields = specNew.getFields().getField();
		
		for (int i = 0; i< nfields.size(); i++) {
			String newNname = nfields.get(i).getName();
			if (newNname != null && newNname.equals(oldname)) {
				ofld = nfields.get(i);
				break;
			}
		}
		 
		return ofld;
	}
	

	/*****************************************************************
	 *  return the old key 
	 ****************************************************************/
	public Key oldKey(Key newfld) {
		
		String newname = newfld.getField();		
		Key ofld = null;
		List<Key> ofields = specOld.getKeys().getKey();
		
		for (int i = 0; i< ofields.size(); i++) {
			String oldname = ofields.get(i).getField();
			if (oldname != null && oldname.equals(newname)) {
				ofld = ofields.get(i);
				break;
			}
		}
		 
		return ofld;
	}
	/*****************************************************************
	 *  return the new key 
	 ****************************************************************/
	public Key newKey(Key oldfld) {
		
		String oldname = oldfld.getField();		
		Key ofld = null;
		List<Key> nfields = specNew.getKeys().getKey();
		
		for (int i = 0; i< nfields.size(); i++) {
			String newNname = nfields.get(i).getField();
			if (newNname != null && newNname.equals(oldname)) {
				ofld = nfields.get(i);
				break;
			}
		}
		 
		return ofld;
	}
	/*****************************************************************
	 *  report selection changed
	 ****************************************************************/
	public boolean reportSelectionChange(List<SpecStatus> specStatus) {

		if (selectionAdded() || selectionRemoved()) {
			OverWriteOption opt = OverWriteOption.OVERWRITE_REQUIRED;
			String msg = "Selections added or removed.";
			specStatus.add(new SpecStatus(msg, opt));
			return true; // added/removed
		}

		if (selectionReordered()) {
			OverWriteOption opt = OverWriteOption.OVERWRITE_REQUIRED;
			String msg = "Selections has been reordered.";
			specStatus.add(new SpecStatus(msg, opt));
			return true;
		}

		return false;
	}
	
	/*****************************************************************
	 *  Selection added 
	 ****************************************************************/
	public boolean selectionAdded() {
		
		List<Selection> nfields = specNew.getSelections().getSelection();
		for (Selection fldn :  nfields) {
			Selection ofld = oldSelection(fldn);
			if (ofld == null) {
				return true; //this new Selection is added 
			}
		}
		 
		return false;
	}
	/*****************************************************************
	 *  Selection removed
	 ****************************************************************/
	public boolean selectionRemoved() {
		
		List<Selection> ofields = specOld.getSelections().getSelection();
		for (Selection fldo :  ofields) {
			Selection fldn = newSelection(fldo);
			if (fldn == null) {
				return true; //this new Selection is removed 
			}
		}
		return false;
	}
	/*****************************************************************
	 *  selection reordered
	 ****************************************************************/
	public boolean selectionReordered() {
		
		List<Selection> ofieldsrmv = removeDeletedSelections();
		List<Selection> nfields = specNew.getSelections().getSelection();
	 
		for (int i = 0; i< ofieldsrmv.size(); i++) {
			String nameo = ofieldsrmv.get(i).getField();	
			int seqo = ofieldsrmv.get(i).getSeq();
			String namen = nfields.get(i).getField();	
			int seqn = nfields.get(i).getSeq();
			
			if (!nameo.equals(namen) || seqo != seqn) {
				return true;
			}
		}
		return false;
	}
	
	/*****************************************************************
	 *  return the old Selection 
	 ****************************************************************/
	public Selection oldSelection(Selection newSelection) {
		
		String newname = newSelection.getField();	
		int newseq = newSelection.getSeq();
		Selection ofld = null;
		List<Selection> ofields = specOld.getSelections().getSelection();
		
		for (int i = 0; i< ofields.size(); i++) {
			String oldname = ofields.get(i).getField();
			int oldseq = ofields.get(i).getSeq();
			if (oldname != null && oldname.equals(newname) && oldseq == newseq) {
				ofld = ofields.get(i);
				break;
			}
		}
		 
		return ofld;
	}
	/*****************************************************************
	 *  return the new Selection 
	 ****************************************************************/
	public Selection newSelection(Selection oldSelection) {
		
		String oldname = oldSelection.getField();		
		int oldseq = oldSelection.getSeq();
		Selection ofld = null;
		List<Selection> nfields = specNew.getSelections().getSelection();
		
		for (int i = 0; i< nfields.size(); i++) {
			String newNname = nfields.get(i).getField();
			int newseq =  nfields.get(i).getSeq();
			if (newNname != null && newNname.equals(oldname) && oldseq == newseq) {
				ofld = nfields.get(i);
				break;
			}
		}
		 
		return ofld;
	}
	
	/*****************************************************************
	 *  Return a list of specified Changes
	 ****************************************************************/
	public List<SpecStatus>  changes(List<SpecStatus> list, OverWriteOption opt) {
		List<SpecStatus> changes = new ArrayList<SpecStatus>();
		for (SpecStatus stat : list) {
			if (stat.getOverWriteOption() == opt) {
				changes.add(stat);
			}
		}		
		return changes;
	}
	
    /********************************************************
     * Test
     *******************************************************/
    public static void main(String[] args)   {
       
		String loc = "C:/m-power_distribution/m-power/mrcjava/WEB-INF/classes/MRCJAVA2";
		AppSpecs as = new AppSpecs(loc, "R00001o_.xml","R00001_.xml", "R");
//		AppSpecs as = new AppSpecs(loc, "M00007o_.xml","M00007_.xml", "M");
		
		List<SpecStatus> specStatus = as.specStatus();
		
		List<SpecStatus> owRequired = as.changes(specStatus, OverWriteOption.OVERWRITE_REQUIRED);
		List<SpecStatus> owNotRequired = as.changes(specStatus, OverWriteOption.NO_OVERWRITE_OR_ACTION_REQUIRED);
		List<SpecStatus> painterAction = as.changes(specStatus, OverWriteOption.NEED_PAINTER_ACTION);
		
		for (SpecStatus st : specStatus) {
			System.out.println( " " + st );
			
		}
	     
        return;
    }
}
