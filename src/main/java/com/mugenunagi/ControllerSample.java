package com.mugenunagi;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;

//import com.mugenunagi.dto.config.AmalbumConfig;

//@Controller
public class ControllerSample {
//    @Autowired
//    private SampleService dog;
//    
//    @RequestMapping("/bark")
//    public String bark( ModelMap modelMap ) {
//    	// サービス呼び出しのサンプル
//    	String barkMessage = dog.bark();
//    	modelMap.addAttribute("message",barkMessage); //$NON-NLS-1$
//    	
//    	List<String> barkList = new ArrayList<String>();
//    	barkList.add( barkMessage );
//    	barkList.add( "にゃん" ); //$NON-NLS-1$
//    	barkList.add( "ぴよぴよ" ); //$NON-NLS-1$
//    	modelMap.addAttribute("barkList",barkList); //$NON-NLS-1$
//    	
//    	// JAXBによる設定読み込みのサンプル
//    	doTest( modelMap );
//    	
//    	return "bark"; //$NON-NLS-1$
//    }
//    
//    private void doTest( ModelMap modelMap ){
//		//
//		FileInputStream inputStream = null;
//		try {
//			inputStream = new FileInputStream( applicationProperties.getString("configFile") ); //$NON-NLS-1$
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		
//		// 読み込む
//		AmalbumConfig config = null;
//		JAXBContext jc = null;
//		try {
//			jc = JAXBContext.newInstance( com.mugenunagi.dto.config.AmalbumConfig.class );
//
//			Unmarshaller u = jc.createUnmarshaller();
//			config = (AmalbumConfig)( u.unmarshal(inputStream) );
//
//			OutputStream os = new FileOutputStream("Output.xml");    //$NON-NLS-1$
//			Marshaller mu = jc.createMarshaller();   
//			mu.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); //改行を入れる
//			mu.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); //文字コードの指定 //$NON-NLS-1$
//			mu.marshal(config, os);
//			os.close();
//		} catch (JAXBException e1) {
//			e1.printStackTrace();
//			return;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		
//		// 閉じる
//		try {
//			inputStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		inputStream=null;
//    }
}
