package org.ouobpo.ouvroir.excel

import scala.collection.JavaConversions._
import scala.reflect._
import java.io._
import net.sf.jxls.reader._

@BeanInfo
case class Data(var column1: String, var column2: String, var column3: String)

object JXLSSample {
  def main(args: Array[String]) {
    val config = <workbook>
                   <worksheet name="Sheet1">
                     <section startRow="0" endRow="1">
                       <mapping cell="A2">data.column1</mapping>
                       <mapping cell="B2">data.column2</mapping>
                       <mapping cell="C2">data.column3</mapping>
                     </section>
                   </worksheet>
                 </workbook>
    val reader = ReaderBuilder.buildFromXML(new ByteArrayInputStream(config.toString.getBytes))
    val data = Data("", "", "")
    val status = reader.read(ClassLoader.getSystemResourceAsStream("sample.xls"), Map("data" -> data))
    printf("status=%s : readMessages=%s", status.isStatusOK, status.getReadMessages)
    println
    println(data)
  }
}