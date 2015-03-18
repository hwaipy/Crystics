/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.input;

import com.hwaipy.crystics.refractivemodel.Range;
import com.hwaipy.crystics.refractivemodel.RefractiveEquation;
import com.hwaipy.crystics.refractivemodel.RefractiveModel;
import com.hwaipy.crystics.refractivemodel.DefaultRefractiveModel;
import com.hwaipy.references.DOI;
import com.hwaipy.references.DOIReference;
import com.hwaipy.references.Reference;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.util.XMLErrorHandler;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Hwaipy
 */
public class SellmeierXMLLoader {

  public static void load(InputStream inputStream) {
    if (entityResolver == null) {
      entityResolver = new EntityResolver() {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
          InputSource ins = new InputSource(SellmeierXMLLoader.class.getResourceAsStream("/com/hwaipy/crystics/Mediums.dtd"));
          ins.setPublicId(publicId);
          ins.setSystemId(systemId);
          return ins;
        }

      };
    }
    SAXReader saxReader = new SAXReader(true);
    saxReader.setEntityResolver(entityResolver);
    XMLErrorHandler errorHandler = new XMLErrorHandler();
    saxReader.setErrorHandler(errorHandler);
    try {
      Document document = saxReader.read(new InputStreamReader(inputStream, "UTF-8"));
      if (errorHandler.getErrors().hasContent()) {
        LoggerFactory.getLogger(SellmeierXMLLoader.class).warn("Input XML data type not valid: " + errorHandler.getErrors().asXML(), errorHandler.getErrors());
        return;
      }
      parse(document);
    } catch (UnsupportedEncodingException ex) {
      LoggerFactory.getLogger(SellmeierXMLLoader.class).warn("Encoding UTF-8 is not supported.", ex);
    } catch (DocumentException ex) {
      LoggerFactory.getLogger(SellmeierXMLLoader.class).warn("XML parsing error.", ex);
    } catch (RuntimeException ex) {
      LoggerFactory.getLogger(SellmeierXMLLoader.class).warn("Runtime error on parsing XML file.", ex);
    }
  }

  private static void parse(Document document) {
    Element rootElement = document.getRootElement();
    Object object = parse(rootElement);
  }

  private static Object parse(Element rootElement) {
    List<Element> mediumElements = rootElement.elements();
    for (Element mediumElement : mediumElements) {
      Object medium = parseMediumElement(mediumElement);
    }

    return null;
  }

  private static Object parseMediumElement(Element mediumElement) {
    String symbol = mediumElement.elementText("symbol");
    String name = mediumElement.elementText("name");
    List<Element> aliasElements = mediumElement.elements("alias");
    ArrayList<String> aliasList = new ArrayList<String>();
    for (Element aliasElement : aliasElements) {
      aliasList.add(aliasElement.getStringValue());
    }
    List<Element> refractiveElements = mediumElement.elements("refractive");
    ArrayList<RefractiveModel> refractiveModels = new ArrayList<RefractiveModel>();
    for (Element refractiveElement : refractiveElements) {
      DefaultRefractiveModel refractiveModel = parseRefractiveElement(refractiveElement);
    }

    return null;
  }

  private static DefaultRefractiveModel parseRefractiveElement(Element refractiveElement) {
    Element rangeElement = refractiveElement.element("range");
    double rangeFrom = Double.parseDouble(rangeElement.elementText("from"));
    double rangeTo = Double.parseDouble(rangeElement.elementText("to"));
    Range range = new Range(rangeFrom, rangeTo);
    //    <!ELEMENT refractive (((nx,ny,nz)|(ne,no)))>
    //<!ELEMENT nx (refractive-model)>
    //<!ELEMENT ny (refractive-model)>
    //<!ELEMENT nz (refractive-model)>
    //<!ELEMENT ne (refractive-model)>
    //<!ELEMENT no (refractive-model)>
    //<!ELEMENT refractive-model ((system|external))>
    //<!ELEMENT system (sellmeier)>
    //<!ELEMENT sellmeier (formula,coefficients)>
    //<!ELEMENT formula (#PCDATA)>
    //<!ELEMENT coefficients (#PCDATA)>
    //<!ELEMENT external (#PCDATA)>
//
    Element nXelement = refractiveElement.element("nx");
    RefractiveEquation refractiveEquationX;
    RefractiveEquation refractiveEquationY;
    RefractiveEquation refractiveEquationZ;
    if (nXelement != null) {
      Element nYelement = refractiveElement.element("ny");
      Element nZelement = refractiveElement.element("nz");
      refractiveEquationX = parseRefractiveEquation(nXelement);
      refractiveEquationY = parseRefractiveEquation(nYelement);
      refractiveEquationZ = parseRefractiveEquation(nZelement);
    }
    else {
      Element nOelement = refractiveElement.element("no");
      Element nEelement = refractiveElement.element("ne");
      refractiveEquationX = parseRefractiveEquation(nEelement);
      refractiveEquationY = refractiveEquationX;
      refractiveEquationZ = parseRefractiveEquation(nOelement);
    }
    Element referenceElement = refractiveElement.element("reference");
    Reference reference = parseReference(referenceElement);
    return new DefaultRefractiveModel(range, reference,
            refractiveEquationX, refractiveEquationY, refractiveEquationZ);
  }

  private static RefractiveEquation parseRefractiveEquation(Element nXelement) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  private static Reference parseReference(Element referencElement) {
    String description = referencElement.elementText("description");
    Element doiElement = referencElement.element("doi");
    if (doiElement != null) {
      DOI doi = new DOI(doiElement.getStringValue());
      return new DOIReference(doi, description);
    }
    else {
      //TODO
      throw new UnsupportedOperationException();
    }
  }

  private static EntityResolver entityResolver;

  public static void main(String[] args) throws Exception {
    load(SellmeierXMLLoader.class.getResourceAsStream("/com/hwaipy/crystics/Mediums.xml"));
  }

}
