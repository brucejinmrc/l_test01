package template_options;

import java.io.File;
import java.util.List;

import javax.xml.bind.*;

public class Demo {

    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance("template_options");
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        
        File xml = new File("xml/template_options.xml");
        JAXBElement<TemplatesType> tests = (JAXBElement<TemplatesType>) unmarshaller.unmarshal(xml);
        TemplatesType tplsType = tests.getValue();

        List<TemplateType>  tpls = tplsType.getTemplate();
        
        TemplateType tpl1 = tpls.get(0);
        System.out.println("name=" + tpl1.getName());
        
        return;
    }

}
