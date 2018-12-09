package com.visutools.nav.bislider;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JApplet;
import javax.swing.JPopupMenu;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import org.w3c.dom.*;

/**
 * HTMLTableColorizerApplet.java
 * 5 examples of BiSlider in a JFrame to see the widget at work.
 * <br><br>
 * <table border=1 width = "90%">
 *   <tr>
 *     <td>
 *       Copyright 1997-2006 Frederic Vernier. All Rights Reserved.<br>
 *       <br>
 *       Permission to use, copy, modify and distribute this software and its documentation for educational, research and
 *       non-profit purposes, without fee, and without a written agreement is hereby granted, provided that the above copyright
 *       notice and the following three paragraphs appear in all copies.<br>
 *       <br>
 *       To request Permission to incorporate this software into commercial products contact
 *       Frederic Vernier, 19 butte aux cailles street, Paris, 75013, France. Tel: (+33) 871 747 387.
 *       eMail: Frederic.Vernier@laposte.net / Web site: http://vernier.frederic.free.fr
 *       <br>
 *       IN NO EVENT SHALL FREDERIC VERNIER BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL
 *       DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF FREDERIC
 *       VERNIER HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.<br>
 *       <br>
 *       FREDERIC VERNIER SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *       MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HERE UNDER IS ON AN "AS IS" BASIS, AND
 *       FREDERIC VERNIER HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.<br>
 *     </td>
 *   </tr>
 * </table>
 * <br>
 * <b>Dates:</b><br>
 *   <li>Creation    : October 15, 2005<br>
 *   <li>Format      : 15/10/2005 <br>
 *   <li>Last Modif  : 19/10/2005 <br>
 *<br>
 * <b>Bugs:</b><br>
 * <li><br>
 *<br>
 * <b>To Do:</b><br>
 *  <li><br>
 *<br>
 * @author Frederic Vernier, Frederic.Vernier@laposte.net
 * @version 1.4.1
 **/

public class HTMLTableColorizerApplet extends JApplet {

  // The code will parse a HTML table we need to know the range and the name
  protected int MinColIndex = 0;
  protected int MaxColIndex = 0;
  protected int MinRowIndex = 0;
  protected int MaxRowIndex = 0;
  protected String LinkToTableString = "";
  protected String RelativeColumnString = "";
  protected int RelColIndex = -1;
  
  
  // let's share the distribution array as well
  protected double[][] DistributionArray = null;
  protected int BiggestClassIndex        = 0;
  protected DecimalFormat DecimalFormat1 = null;
  

  /** 
   * parse a html color name and return a java <code>Color</code>
   */
  private Color parseColorStr(String ColorName) {
    int r, g, b;
    // Convert a string in the standard HTML "#RRBBGG" format to a valid
    // color value, if possible.
    if (ColorName.length() == 7 && ColorName.charAt(0) == '#') {
      try {
        r = Integer.parseInt(ColorName.substring(1,3),16);
        g = Integer.parseInt(ColorName.substring(3,5),16);
        b = Integer.parseInt(ColorName.substring(5,7),16);
        return(new Color(r, g, b));
      }
      catch (Exception Exception_Arg) {
        Exception_Arg.printStackTrace();
      }
    }
    // Otherwise, default to black.
    return(Color.BLACK);
  }// parseColorStr()
 
 
  /** Creates a new instance of HTMLTableColorizerApplet */
  public HTMLTableColorizerApplet() {
  }
  
  
  /**
   * standard method to return info about the applet
   **/
  public String getAppletInfo(){
    return "BiSlider applet display a BiSlider bean to control an HTML table";
  }  
  
  
  /**
   * entry point fot the applet
   */
  public void init() {
    // get parameters from the applet param tags if exist
    String MinimumColorString        = getParameter("MinimumColor");
    String MaximumColorString        = getParameter("MaximumColor");
    String MinimumValueString        = getParameter("MinimumValue");    
    String MaximumValueString        = getParameter("MaximumValue");    
    String MinimumColoredValueString = getParameter("MinimumColoredValue");    
    String MaximumColoredValueString = getParameter("MaximumColoredValue");    
    
    String ArcSizeString             = getParameter("ArcSize");    
    String UniformSegmentString      = getParameter("UniformSegment");    
    String SegmentSizeString         = getParameter("SegmentSize");    
    String InterpolationModeString   = getParameter("InterpolationMode");    
    
    String CustomPaintString         = getParameter("CustomPaint");    
           LinkToTableString         = getParameter("LinkToTable"); 
           RelativeColumnString      = getParameter("RelativeColumn"); 
    String TableWidthString          = getParameter("TableWidth");    
    String TableHeightString         = getParameter("TableHeight");  
    String DecimalFormatString       = getParameter("DecimalFormat");       
    
    String MinColIndexString         = getParameter("MinColIndex");
    String MaxColIndexString         = getParameter("MaxColIndex");
    String MinRowIndexString         = getParameter("MinRowIndex");
    String MaxRowIndexString         = getParameter("MaxRowIndex");
    
    String PreciseString             = getParameter("PreciseString");    
    String UnitString                = getParameter("UnitString");       
    /*
    DOMService service = null;
    try
    {
        service = DOMService.getService(MyApplet);
        String title = (String) service.invokeAndWait(new DOMAction()
                                {
                                    public Object run(DOMAccessor accessor)
                                    {
                                         HTMLDocument doc = (HTMLDocument) accessor.getDocument(MyApplet);
                                         return doc.getTitle();
                                    }
                                });
    }
    catch (DOMUnsupportedException e1)
    {
    }
    catch (DOMAccessException e2)
    {
}*/
    
    if (DecimalFormatString  !=null)
      DecimalFormat1 = new DecimalFormat(DecimalFormatString);
    
    final BiSlider BiSliderWeb = new BiSlider(BiSlider.CENTRAL);
    if (InterpolationModeString!=null && InterpolationModeString.toLowerCase().equals("hsb"))
      BiSliderWeb.setInterpolationMode(BiSlider.HSB);
    else if (InterpolationModeString!=null && InterpolationModeString.toLowerCase().equals("rgb"))
      BiSliderWeb.setInterpolationMode(BiSlider.RGB);
    
    //-----------------------------------------------------------------------
    if (UniformSegmentString!=null)
      try {
        BiSliderWeb.setUniformSegment(Boolean.parseBoolean(UniformSegmentString));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    else    
      BiSliderWeb.setUniformSegment(true);
    //-----------------------------------------------------------------------
    BiSliderWeb.setVisible(true);
    //-----------------------------------------------------------------------
    if (MinimumValueString!=null)
      try {
        BiSliderWeb.setMinimumValue(Integer.parseInt(MinimumValueString));
      } catch (NumberFormatException NumberFormatException_Arg) {
        NumberFormatException_Arg.printStackTrace();
      }
    else
      BiSliderWeb.setMinimumValue(0);
    //-----------------------------------------------------------------------
    if (MaximumValueString!=null)
      try {
        BiSliderWeb.setMaximumValue(Integer.parseInt(MaximumValueString));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    else
      BiSliderWeb.setMaximumValue(100);
    //-----------------------------------------------------------------------
    if (SegmentSizeString!=null)
      try {
        BiSliderWeb.setSegmentSize(Integer.parseInt(SegmentSizeString));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    else
      BiSliderWeb.setSegmentSize(40);
    //-----------------------------------------------------------------------
    if (MinimumColorString!=null)
      BiSliderWeb.setMinimumColor(parseColorStr(MinimumColorString));
    else
      BiSliderWeb.setMinimumColor(Color.RED);
    //-----------------------------------------------------------------------
    if (MaximumColorString!=null)
      BiSliderWeb.setMaximumColor(parseColorStr(MaximumColorString));
    else
      BiSliderWeb.setMaximumColor(Color.BLUE);
    //-----------------------------------------------------------------------
    if (MinimumColoredValueString!=null)
      try {
        BiSliderWeb.setColoredValues(Integer.parseInt(MinimumColoredValueString), Integer.parseInt(MinimumColoredValueString));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    else
      BiSliderWeb.setColoredValues(0, 100);
    //-----------------------------------------------------------------------
    if (MaximumColoredValueString!=null)
      try {
        BiSliderWeb.setColoredValues(BiSliderWeb.getMinimumColoredValue(), Integer.parseInt(MaximumColoredValueString));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    else
      BiSliderWeb.setColoredValues(BiSliderWeb.getMinimumColoredValue(), BiSliderWeb.getMinimumColoredValue()+100);  

    //-----------------------------------------------------------------------    
    if (UnitString!=null)
      BiSliderWeb.setUnit(UnitString);
    else
      BiSliderWeb.setUnit("");
    BiSliderWeb.setBackground(Color.WHITE);

    //-----------------------------------------------------------------------    
    if (PreciseString!=null)
      try {
        BiSliderWeb.setPrecise(Boolean.parseBoolean(PreciseString));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    else    
      BiSliderWeb.setPrecise(true);
    
    //-----------------------------------------------------------------------
    if (ArcSizeString!=null)
      try {
        BiSliderWeb.setArcSize(Integer.parseInt(ArcSizeString));
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
    else
      BiSliderWeb.setArcSize(0);

    //-----------------------------------------------------------------------    
    try {
      if (CustomPaintString!=null && Boolean.parseBoolean(CustomPaintString)) {
        if (LinkToTableString!=null) {
          MinColIndex = Integer.parseInt(MinColIndexString);
          MaxColIndex = Integer.parseInt(MaxColIndexString);
          MinRowIndex = Integer.parseInt(MinRowIndexString);
          MaxRowIndex = Integer.parseInt(MaxRowIndexString);
          
          // if we do relative colorization, we display percentages
          if (RelativeColumnString!=null && !RelativeColumnString.equals("")){
            RelColIndex = Integer.parseInt(RelativeColumnString);
            /*BiSliderWeb.setMinimumValue(0);
            BiSliderWeb.setMaximumValue(100);*/
          }
          
          BiSliderWeb.setToolTipText("The background is the histogramm of the HTML cells");
          
          initHTMLLink();
          updateHTMLLink(BiSliderWeb);
        } else 
          BiSliderWeb.setToolTipText("The background is a customizable histogramm");    

        BiSliderWeb.addContentPainterListener(new ContentPainterListener(){
          public void paint(ContentPainterEvent ContentPainterEvent_Arg){
            Graphics2D Graphics2 = (Graphics2D)ContentPainterEvent_Arg.getGraphics();

            int index = ContentPainterEvent_Arg.getSegmentIndex();
            double Rand = 1.0;
            Rectangle Rect1 = ContentPainterEvent_Arg.getRectangle();
            Rectangle Rect2 = ContentPainterEvent_Arg.getBoundingRectangle();

            // normal distribution
            if (DistributionArray!=null) {
              Rand = 1-(DistributionArray[index][2]/DistributionArray[BiggestClassIndex][2]);
            } else {
              float X = ((float)Rect2.x-BiSliderWeb.getWidth()/2)/BiSliderWeb.getWidth()*6;
              Rand = 1-Math.exp((-1*X*X)/2);
            }

            if (ContentPainterEvent_Arg.getColor()!=null) {
              Graphics2.setColor(BiSliderWeb.getSliderBackground().darker());
              Graphics2.fillRect(Rect2.x, Rect2.y, Rect2.width, (int)((Rand*Rect2.height)));
              Graphics2.setColor(ContentPainterEvent_Arg.getColor());
              Graphics2.fillRect(Rect2.x, Rect2.y+(int)((Rand*Rect2.height)), Rect2.width-1, (int)(((1-Rand)*Rect2.height)));
            }
            Graphics2.setColor(Color.BLACK);
            Graphics2.drawRect(Rect2.x, Rect2.y+(int)((Rand*Rect2.height)), Rect2.width-1, (int)(((1-Rand)*Rect2.height)));
          }
        });
      }
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
    }
    
    if (LinkToTableString!=null) {
      final JApplet TheApplet = this;
      BiSliderWeb.addBiSliderListener(new BiSliderAdapter() {
        public void newColors(BiSliderEvent ColorisationEvent_Arg) {
          updateHTMLLink(BiSliderWeb);
        }
      });
    }
    
    final JPopupMenu JPopupMenu6 = BiSliderWeb.createPopupMenu();
    BiSliderWeb.addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent MouseEvent_Arg){
        if (MouseEvent_Arg.getButton()==MouseEvent.BUTTON3){
          JPopupMenu6.show(BiSliderWeb, MouseEvent_Arg.getX(), MouseEvent_Arg.getY());
        }
      }
    });
    
    setLayout(new BorderLayout());
    add(BiSliderWeb, BorderLayout.CENTER);
  }// init()
  

  protected JSObject JSTable    = null;
  protected JSObject JSWindow   = null;
  
  
  
  /**
   * initialize the Java-Javascript bridge
   */
  public void initHTMLLink(){
    JSObject JSDocument = null;
    String JSBrowerName = null;
    System.out.println("initHTMLLink ");    
    
    try {
      JSWindow     =            JSObject.getWindow(this);
      System.out.println("JSWindow = " +JSWindow);  
      JSWindow.eval("alert('Applet demaree');");
      JSDocument   = (JSObject) JSWindow.getMember("document");    
      System.out.println("mem0 = " +JSWindow.getSlot(0));
      System.out.println("mem1 = " +JSWindow.getSlot(1));
      System.out.println("mem2 = " +JSWindow.getSlot(2));  
      System.out.println("JSDocument = " +JSDocument);    
      JSBrowerName = (String)   JSWindow.eval("window.navigator.appName.toLowerCase()");
      JSObject HTMLInputText = (JSObject) JSWindow.eval("document");
      System.out.println("JSDocument2 = " +JSWindow.getMember("parent")); 
      System.out.println("JSBrowerName = " +JSBrowerName);    

    } catch (JSException JSException_Arg){
      JSException_Arg.printStackTrace();
      System.out.println("ex"+JSException_Arg.getWrappedExceptionType()+"="+JSException_Arg.getMessage());
    }

    String BrowserName  = System.getProperty("browser");
    //if (JSBrowerName.startsWith("netscape")) ...

    try {
      JSTable = (JSObject) JSWindow.eval("document.getElementById('"+LinkToTableString+"')");
      System.out.println("JSTable = " +JSTable+"   =?null :"+(JSTable==null)+" got with "+LinkToTableString);
    } catch (JSException JSException_Arg){
      JSException_Arg.printStackTrace();
      System.out.println("ex"+JSException_Arg.getWrappedExceptionType()+"="+JSException_Arg.getMessage());
    }       
  }// initHTMLLink()
  
  
  /**
   * update the distribution histogramm and paint the HTML table
   */
  public void updateHTMLLink(BiSlider BiSlider_Arg){
    
    // let's prepare a distribution table with 0 in all classes and no biggest class
    Colorizer Colorizer1 = BiSlider_Arg.getColorizer();
    DistributionArray = new double[BiSlider_Arg.getSegmentCount()][3];
    for (int k=0; k<BiSlider_Arg.getSegmentCount(); k++) {
      DistributionArray[k][0] = k*BiSlider_Arg.getSegmentSize();
      DistributionArray[k][1] = (k+1)*BiSlider_Arg.getSegmentSize();
      DistributionArray[k][2] = 0;
    }
    BiggestClassIndex = 0;    

    try {
      // The user chose to indicate the full html table with <table id="MyTableName"...> 
      // On mac the jstable can be non null but toString return null and nothing works !
      if (JSTable!=null && JSTable.toString()!=null)
        updateHTMLLinkFullTable(BiSlider_Arg, JSTable, Colorizer1);

      // The user chose to indicate the html table cell by cell with <td id="MyTableName1-1"...> ...<td id="MyTableName1-2"...> 
      else 
        updateHTMLLinkCellByCell(BiSlider_Arg, JSWindow, Colorizer1);
    } catch (JSException JSException_Arg){
      JSException_Arg.printStackTrace();
      System.out.println("ex"+JSException_Arg.getWrappedExceptionType()+"="+JSException_Arg.getMessage());
    }    
  }// updateHTMLLink()
  
  
  /**
   * update the applet and the HTML <table>
   */
  public void updateHTMLLink(){
    try {
      // The user chose to indicate the full html table with <table id="MyTableName"...> 
      if (JSTable!=null){
        JSObject childNodes=(JSObject)JSTable.getMember("childNodes");

        // There are maybe whitespaces or other comments before we reach the tbody !
        // we'll use this technique for tr and td parsing as well
        int nodeindex = 0;
        for (; (nodeindex<10&&childNodes.getSlot(nodeindex) instanceof JSObject && 
                !childNodes.getSlot(nodeindex).toString().equals("[object HTMLTableSectionElement]")); nodeindex++);
          System.out.println("son of table : "+childNodes.getSlot(nodeindex));
        if (nodeindex==10){System.out.println("Error when looking for [object HTMLTableSectionElement] in the dom");return;}

        // So we get the JSObjecvt where we found it
        JSObject JSTBODYObject = (JSObject)childNodes.getSlot(nodeindex);

        int rownodeindex = -1;
        for (int j=0; j<=MaxRowIndex; j++) {
          childNodes=(JSObject)JSTBODYObject.getMember("childNodes");  

          for (rownodeindex++; (rownodeindex<4*MaxRowIndex&&childNodes.getSlot(rownodeindex) instanceof JSObject && 
                  !childNodes.getSlot(rownodeindex).toString().equals("[object HTMLTableRowElement]")); rownodeindex++);
          if (rownodeindex==4*MaxRowIndex){System.out.println("Error when looking for [object HTMLTableRowElement] in the dom: not enough columns?");return;}

          JSObject JSTRNode=(JSObject)childNodes.getSlot(rownodeindex);

          // if we are after the 1rst row to consider
          if (j>=MinRowIndex) {
            int colnodeindex = -1;
            for (int i=0; i<=MaxColIndex; i++) {
              childNodes=(JSObject)JSTRNode.getMember("childNodes");

              for (colnodeindex++; (colnodeindex<4*MaxColIndex&&childNodes.getSlot(colnodeindex) instanceof JSObject && 
                      !childNodes.getSlot(colnodeindex).toString().equals("[object HTMLTableCellElement]")); colnodeindex++);
              if (colnodeindex==4*MaxColIndex){System.out.println("Error when looking for [object HTMLTableCellElement] in the dom: not enough columns?");return;}

              // if we are after the 1rst column to consider
              if (i>=MinColIndex) {
                JSObject JSTDNode=(JSObject)childNodes.getSlot(colnodeindex);

                 if (JSTDNode!=null) {
                  String Value     = (String)JSTDNode.getMember("innerHTML");
                  while (Value.indexOf("<")>=0 &&Value.indexOf(">")>=0 && Value.indexOf("<")<Value.indexOf(">"))
                    Value= Value.substring(0, Value.indexOf("<"))+Value.substring(Value.indexOf(">"));
                  String ColorName = "#FFFFFF";

                  if (Value!=null){
                    // convert the value 
                    double val =0;
                    try {                      
                      if (DecimalFormat1!=null)                      
                        val = DecimalFormat1.parse(Value).doubleValue();      
                      else {
                        //System.out.println("parse french2 ");
                        NumberFormat NumberFormat1 = NumberFormat.getInstance(Locale.FRENCH);
                        val = NumberFormat1.parse(Value.replace(' ', '\u00A0')).doubleValue();   
                      }
                      //System.out.println("Value:"+Value+" = >"+val);
                      val = Integer.parseInt(Value);
                    } catch (NumberFormatException NumberFormatException_Arg) {
                      NumberFormatException_Arg.printStackTrace();
                      return;
                    } catch (ParseException ParseException_Arg) {
                      ParseException_Arg.printStackTrace();
                      return;
                    }

                    // from Color to html format #FF0088
                    Color col = Color.RED;
                    if (col!=null) {
                      String RedString   = Integer.toHexString(col.getRed());
                      String GreenString = Integer.toHexString(col.getGreen());
                      String BlueString  = Integer.toHexString(col.getBlue());
                      if (RedString.length()==1)   RedString   = "0"+RedString;
                      if (GreenString.length()==1) GreenString = "0"+GreenString;
                      if (BlueString.length()==1)  BlueString  = "0"+BlueString;
                      ColorName = "#"+RedString+GreenString+BlueString;
                    }

                    // update the applet here
                  }// if Value!=null

                  // update the parameters of the html cell
                  JSTDNode.setMember("bgColor", ColorName);
                  //JSCell_Arg.setMember("align",   "right");
                }// if JSTDNode != null
              }// if we are after the 1rst column to consider
            }// for loop on columns
          }// if we are after the 1rst row to consider
        }// for loop on rows
      }// if JSTable!=null
    } catch (JSException JSException_Arg){
      JSException_Arg.printStackTrace();
      System.out.println("ex"+JSException_Arg.getWrappedExceptionType()+"="+JSException_Arg.getMessage());
    } catch(Exception Exception_Arg) {
       Exception_Arg.printStackTrace();
    }
  }// updateHTMLLink()
  
  
  /**
   * update the table by going through the DOM table
   */
  protected void updateHTMLLinkFullTable(
    BiSlider BiSlider_Arg, 
    JSObject JSTable_Arg,
    Colorizer Colorizer_Arg) {

    //System.out.println("  updateHTMLLinkFullTable "+JSTable_Arg);    
    
    try {
      System.out.println("    childNodes "+JSTable_Arg);
      JSObject childNodes=(JSObject)JSTable_Arg.getMember("childNodes");
 
      // There are maybe whitespaces or other comments before we reach the tbody !
      // we'll use this technique for tr and td parsing as well
      int nodeindex = 0;
      for (; (nodeindex<10 && 
              childNodes.getSlot(nodeindex) instanceof JSObject && 
              childNodes.getSlot(nodeindex).toString() !=null && 
              !childNodes.getSlot(nodeindex).toString().equals("[object HTMLTableSectionElement]") &&
              !childNodes.getSlot(nodeindex).toString().equals("[object TBODY]")); nodeindex++){
        //System.out.println("slot "+nodeindex+" = "+childNodes.getSlot(nodeindex));
      }
      if (nodeindex==10){System.out.println("!Error when looking for [object HTMLTableSectionElement] in the dom");return;}
 
      // So we get the JSObject where we found it
      JSObject JSTBODYObject = (JSObject)childNodes.getSlot(nodeindex);
 
      int rownodeindex = -1;
      for (int j=0; j<=MaxRowIndex; j++) {
        childNodes=(JSObject)JSTBODYObject.getMember("childNodes");  

        for (rownodeindex++; (rownodeindex<4*MaxRowIndex&&childNodes.getSlot(rownodeindex) instanceof JSObject && 
                              !childNodes.getSlot(rownodeindex).toString().equals("[object HTMLTableRowElement]") &&
                              !childNodes.getSlot(rownodeindex).toString().equals("[object TR]"));
                         
             rownodeindex++)
          System.out.println("slot "+rownodeindex+" = "+childNodes.getSlot(rownodeindex));
        if (rownodeindex==4*MaxRowIndex){System.out.println("Error when looking for [object HTMLTableRowElement] in the dom: not enough columns?");return;}
        
        JSObject JSTRNode=(JSObject)childNodes.getSlot(rownodeindex);

        // if we are after the 1rst row to consider
        if (j>=MinRowIndex) {
          int colnodeindex = -1;
          JSObject JSTDNodeRel = null;
          for (int i=0; i<=MaxColIndex; i++) {
            childNodes=(JSObject)JSTRNode.getMember("childNodes");
                    
            for (colnodeindex++; (colnodeindex<4*MaxColIndex&&childNodes.getSlot(colnodeindex) instanceof JSObject && 
                    !childNodes.getSlot(colnodeindex).toString().equals("[object HTMLTableCellElement]") &&
                    !childNodes.getSlot(colnodeindex).toString().equals("[object TD]")); colnodeindex++){
            }
            // this is not an useless cell, this is the cell we are relative to (if specified)
            //System.out.println("test "+colnodeindex+"%"+RelColIndex+"%"+i+ " with "+((JSObject)childNodes.getSlot(colnodeindex)).getMember("innerHTML"));
          if (i==RelColIndex)
              JSTDNodeRel=(JSObject)childNodes.getSlot(colnodeindex);            
            
            if (colnodeindex==4*MaxColIndex){System.out.println("Error when looking for [object HTMLTableCellElement] in the dom: not enough columns?");return;}

            // if we are after the 1rst column to consider
            if (i>=MinColIndex) {
              JSObject JSTDNode=(JSObject)childNodes.getSlot(colnodeindex);
              updateCell(BiSlider_Arg, JSTDNode, JSTDNodeRel, Colorizer_Arg);
            }// if we are after the 1rst column to consider
          }// for loop on columns
        }// if we are after the 1rst row to consider
      }// for loop on rows
 
    } catch (JSException JSException_Arg){
      JSException_Arg.printStackTrace();
      System.out.println("ex"+JSException_Arg.getWrappedExceptionType()+"="+JSException_Arg.getMessage());
    }
    catch(Exception Exception_Arg) {
      Exception_Arg.printStackTrace();
    }
  }// updateHTMLLinkFullTable()


   /**
   * update the table by going through the DOM table
   */
  protected void updateHTMLLinkCellByCell(
    BiSlider BiSlider_Arg, 
    JSObject JSWindow,
    Colorizer Colorizer_Arg) {  
    
    try {
      for (int i=MinColIndex; i<=MaxColIndex; i++)
        for (int j=MinRowIndex; j<=MaxRowIndex; j++) {
          // get the value in the html table
          JSObject JSCell    = (JSObject) JSWindow.eval("document.getElementById('"+LinkToTableString+i+"-"+j+"')");
          JSObject JSCellRel = null;
          
          if (RelColIndex!=-1)
            JSCellRel = (JSObject) JSWindow.eval("document.getElementById('"+LinkToTableString+RelColIndex+"-"+j+"')");
          
          //if (JSCellRel!=null && JSCellRel.toString()!=null)
            updateCell(BiSlider_Arg, JSCell, JSCellRel, Colorizer_Arg);
          //else 
          //  System.out.println(" retrieved cell null = document.getElementById('"+LinkToTableString+RelColIndex+"-"+j+"')");
        }
    } catch (Exception Exception_Arg) {
      Exception_Arg.printStackTrace();
    }
  }// updateHTMLLinkCellByCell();


   /**
   * update the table by going through the DOM table
   */
  protected void updateCell(
    BiSlider BiSlider_Arg, 
    JSObject JSCell_Arg,
    JSObject JSCellRel_Arg,
    Colorizer Colorizer_Arg) {  

    //System.out.println("  updateCell "+JSCell_Arg);
    
    if (JSCellRel_Arg!=null) 
      System.out.println(" JSCellRel_Arg = "+JSCellRel_Arg);
    
    //System.out.println("  JSCell_Arg "+JSCell_Arg.hashCode());
    if (JSCell_Arg!=null) {
      String Value     = (String)JSCell_Arg.getMember("innerHTML");
      if (Value==null)
        System.out.println("  JSCell_Arg VALUE NULL : "+JSCell_Arg);

      // retrieve the reference value
      double relval = 1;      
      if (JSCellRel_Arg!=null) {
        String RelValue     = (String)JSCellRel_Arg.getMember("innerHTML");
          while (RelValue.indexOf("<")>=0 &&RelValue.indexOf(">")>=0 && RelValue.indexOf("<")<RelValue.indexOf(">"))
            RelValue= RelValue.substring(0, RelValue.indexOf("<"))+RelValue.substring(RelValue.indexOf(">")+1);
        RelValue = RelValue.replaceAll("\n", "");
        RelValue = RelValue.replaceAll("\r", "");
        RelValue = RelValue.trim();        
        if (RelValue!=null){
          // convert the RelValue 
          try {
            if (DecimalFormat1!=null)
              relval = DecimalFormat1.parse(RelValue).doubleValue();    
            else {
              NumberFormat NumberFormat1 = NumberFormat.getInstance(Locale.FRENCH);
              relval = NumberFormat1.parse(RelValue.replace(' ', '\u00A0')).doubleValue();   
            }
          } catch (NumberFormatException NumberFormatException_Arg) {
            NumberFormatException_Arg.printStackTrace();
            return;
          } catch (ParseException ParseException_Arg) {
            ParseException_Arg.printStackTrace();
            return;
          }
        }
      }
         
      while (Value.indexOf("<")>=0 &&Value.indexOf(">")>=0 && Value.indexOf("<")<Value.indexOf(">"))
        Value= Value.substring(0, Value.indexOf("<"))+Value.substring(Value.indexOf(">")+1);
      Value = Value.replaceAll("\n", "");
      Value = Value.replaceAll("\r", "");
      Value = Value.trim();
//System.out.println("  Value2="+Value);
      String ColorName = "#FFFFFF";
      
      if (Value!=null){
        // convert the value 
        double val =0;
        try {
          if (DecimalFormat1!=null)
            val = DecimalFormat1.parse(Value).doubleValue();    
          else {
            //System.out.println("parse french2");
            NumberFormat NumberFormat1 = NumberFormat.getInstance(Locale.FRENCH);
            val = NumberFormat1.parse(Value.replace(' ', '\u00A0')).doubleValue();   
          }

          //System.out.println("  Value:"+Value+" = >"+val);
          
          //val = Integer.parseInt(Value);
        } catch (NumberFormatException NumberFormatException_Arg) {
          NumberFormatException_Arg.printStackTrace();
          return;
        } catch (ParseException ParseException_Arg) {
          ParseException_Arg.printStackTrace();
          return;
        }

        //System.out.println("rel? "+JSCellRel_Arg);
        
        // if we are relative, we do percentage 
        if (JSCellRel_Arg!=null)
          val = val*100 / relval;
        
        // from Color to html format #FF0088
        Color col = Colorizer_Arg.getColorForValue(val);
        if (col!=null) {
          String RedString   = Integer.toHexString(col.getRed());
          String GreenString = Integer.toHexString(col.getGreen());
          String BlueString  = Integer.toHexString(col.getBlue());
          if (RedString.length()==1)   RedString   = "0"+RedString;
          if (GreenString.length()==1) GreenString = "0"+GreenString;
          if (BlueString.length()==1)  BlueString  = "0"+BlueString;
          ColorName = "#"+RedString+GreenString+BlueString;
        }

        // update the distribution table 
        for (int k=0; k<DistributionArray.length; k++){
          if (val>=DistributionArray[k][0] && val<DistributionArray[k][1]){
            DistributionArray[k][2] = DistributionArray[k][2]+1;
            break;
          }
          
          // update the biggest class index
          if (DistributionArray[k][2]>DistributionArray[BiggestClassIndex][2])
            BiggestClassIndex = k;
        }// update the distribution table 
      }// if Value!=null
      
      // update the parameters of thwe html cell
      JSCell_Arg.setMember("bgColor", ColorName);
      //JSCell_Arg.setMember("align",   "right");
    }
  }// updateCell()
  
}// class HTMLTableColorizerApplet
