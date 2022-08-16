begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Closeable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|Attribute
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|Code
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|CodeException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantPool
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantUtf8
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantValue
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ExceptionTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|InnerClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|InnerClasses
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|LineNumber
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|LineNumberTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|LocalVariable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|LocalVariableTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|SourceFile
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|Utility
import|;
end_import

begin_comment
comment|/**  * Convert found attributes into HTML file.  */
end_comment

begin_class
specifier|final
class|class
name|AttributeHTML
implements|implements
name|Closeable
block|{
specifier|private
specifier|final
name|String
name|className
decl_stmt|;
comment|// name of current class
specifier|private
specifier|final
name|PrintWriter
name|printWriter
decl_stmt|;
comment|// file to write to
specifier|private
name|int
name|attrCount
decl_stmt|;
specifier|private
specifier|final
name|ConstantHTML
name|constantHtml
decl_stmt|;
specifier|private
specifier|final
name|ConstantPool
name|constantPool
decl_stmt|;
name|AttributeHTML
parameter_list|(
specifier|final
name|String
name|dir
parameter_list|,
specifier|final
name|String
name|className
parameter_list|,
specifier|final
name|ConstantPool
name|constantPool
parameter_list|,
specifier|final
name|ConstantHTML
name|constantHtml
parameter_list|,
specifier|final
name|Charset
name|charset
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|className
operator|=
name|className
expr_stmt|;
name|this
operator|.
name|constantPool
operator|=
name|constantPool
expr_stmt|;
name|this
operator|.
name|constantHtml
operator|=
name|constantHtml
expr_stmt|;
name|printWriter
operator|=
operator|new
name|PrintWriter
argument_list|(
name|dir
operator|+
name|className
operator|+
literal|"_attributes.html"
argument_list|,
name|charset
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|print
argument_list|(
literal|"<HTML><head><meta charset=\""
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|print
argument_list|(
name|charset
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"\"></head>"
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"<BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{
name|printWriter
operator|.
name|println
argument_list|(
literal|"</TABLE></BODY></HTML>"
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|private
name|String
name|codeLink
parameter_list|(
specifier|final
name|int
name|link
parameter_list|,
specifier|final
name|int
name|method_number
parameter_list|)
block|{
return|return
literal|"<A HREF=\""
operator|+
name|className
operator|+
literal|"_code.html#code"
operator|+
name|method_number
operator|+
literal|"@"
operator|+
name|link
operator|+
literal|"\" TARGET=Code>"
operator|+
name|link
operator|+
literal|"</A>"
return|;
block|}
name|void
name|writeAttribute
parameter_list|(
specifier|final
name|Attribute
name|attribute
parameter_list|,
specifier|final
name|String
name|anchor
parameter_list|)
block|{
name|writeAttribute
argument_list|(
name|attribute
argument_list|,
name|anchor
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|void
name|writeAttribute
parameter_list|(
specifier|final
name|Attribute
name|attribute
parameter_list|,
specifier|final
name|String
name|anchor
parameter_list|,
specifier|final
name|int
name|method_number
parameter_list|)
block|{
specifier|final
name|byte
name|tag
init|=
name|attribute
operator|.
name|getTag
argument_list|()
decl_stmt|;
name|int
name|index
decl_stmt|;
if|if
condition|(
name|tag
operator|==
name|Const
operator|.
name|ATTR_UNKNOWN
condition|)
block|{
return|return;
block|}
name|attrCount
operator|++
expr_stmt|;
comment|// Increment number of attributes found so far
if|if
condition|(
name|attrCount
operator|%
literal|2
operator|==
literal|0
condition|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"<TR BGCOLOR=\"#C0C0C0\"><TD>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"<TR BGCOLOR=\"#A0A0A0\"><TD>"
argument_list|)
expr_stmt|;
block|}
name|printWriter
operator|.
name|println
argument_list|(
literal|"<H4><A NAME=\""
operator|+
name|anchor
operator|+
literal|"\">"
operator|+
name|attrCount
operator|+
literal|" "
operator|+
name|Const
operator|.
name|getAttributeName
argument_list|(
name|tag
argument_list|)
operator|+
literal|"</A></H4>"
argument_list|)
expr_stmt|;
comment|/*          * Handle different attributes          */
switch|switch
condition|(
name|tag
condition|)
block|{
case|case
name|Const
operator|.
name|ATTR_CODE
case|:
specifier|final
name|Code
name|c
init|=
operator|(
name|Code
operator|)
name|attribute
decl_stmt|;
comment|// Some directly printable values
name|printWriter
operator|.
name|print
argument_list|(
literal|"<UL><LI>Maximum stack size = "
operator|+
name|c
operator|.
name|getMaxStack
argument_list|()
operator|+
literal|"</LI>\n<LI>Number of local variables = "
operator|+
name|c
operator|.
name|getMaxLocals
argument_list|()
operator|+
literal|"</LI>\n<LI><A HREF=\""
operator|+
name|className
operator|+
literal|"_code.html#method"
operator|+
name|method_number
operator|+
literal|"\" TARGET=Code>Byte code</A></LI></UL>\n"
argument_list|)
expr_stmt|;
comment|// Get handled exceptions and list them
specifier|final
name|CodeException
index|[]
name|ce
init|=
name|c
operator|.
name|getExceptionTable
argument_list|()
decl_stmt|;
specifier|final
name|int
name|len
init|=
name|ce
operator|.
name|length
decl_stmt|;
if|if
condition|(
name|len
operator|>
literal|0
condition|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"<P><B>Exceptions handled</B><UL>"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|CodeException
name|cex
range|:
name|ce
control|)
block|{
specifier|final
name|int
name|catchType
init|=
name|cex
operator|.
name|getCatchType
argument_list|()
decl_stmt|;
comment|// Index in constant pool
name|printWriter
operator|.
name|print
argument_list|(
literal|"<LI>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|catchType
operator|!=
literal|0
condition|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
name|constantHtml
operator|.
name|referenceConstant
argument_list|(
name|catchType
argument_list|)
argument_list|)
expr_stmt|;
comment|// Create Link to _cp.html
block|}
else|else
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"Any Exception"
argument_list|)
expr_stmt|;
block|}
name|printWriter
operator|.
name|print
argument_list|(
literal|"<BR>(Ranging from lines "
operator|+
name|codeLink
argument_list|(
name|cex
operator|.
name|getStartPC
argument_list|()
argument_list|,
name|method_number
argument_list|)
operator|+
literal|" to "
operator|+
name|codeLink
argument_list|(
name|cex
operator|.
name|getEndPC
argument_list|()
argument_list|,
name|method_number
argument_list|)
operator|+
literal|", handled at line "
operator|+
name|codeLink
argument_list|(
name|cex
operator|.
name|getHandlerPC
argument_list|()
argument_list|,
name|method_number
argument_list|)
operator|+
literal|")</LI>"
argument_list|)
expr_stmt|;
block|}
name|printWriter
operator|.
name|print
argument_list|(
literal|"</UL>"
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|Const
operator|.
name|ATTR_CONSTANT_VALUE
case|:
name|index
operator|=
operator|(
operator|(
name|ConstantValue
operator|)
name|attribute
operator|)
operator|.
name|getConstantValueIndex
argument_list|()
expr_stmt|;
comment|// Reference _cp.html
name|printWriter
operator|.
name|print
argument_list|(
literal|"<UL><LI><A HREF=\""
operator|+
name|className
operator|+
literal|"_cp.html#cp"
operator|+
name|index
operator|+
literal|"\" TARGET=\"ConstantPool\">Constant value index("
operator|+
name|index
operator|+
literal|")</A></UL>\n"
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ATTR_SOURCE_FILE
case|:
name|index
operator|=
operator|(
operator|(
name|SourceFile
operator|)
name|attribute
operator|)
operator|.
name|getSourceFileIndex
argument_list|()
expr_stmt|;
comment|// Reference _cp.html
name|printWriter
operator|.
name|print
argument_list|(
literal|"<UL><LI><A HREF=\""
operator|+
name|className
operator|+
literal|"_cp.html#cp"
operator|+
name|index
operator|+
literal|"\" TARGET=\"ConstantPool\">Source file index("
operator|+
name|index
operator|+
literal|")</A></UL>\n"
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ATTR_EXCEPTIONS
case|:
comment|// List thrown exceptions
specifier|final
name|int
index|[]
name|indices
init|=
operator|(
operator|(
name|ExceptionTable
operator|)
name|attribute
operator|)
operator|.
name|getExceptionIndexTable
argument_list|()
decl_stmt|;
name|printWriter
operator|.
name|print
argument_list|(
literal|"<UL>"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|int
name|indice
range|:
name|indices
control|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"<LI><A HREF=\""
operator|+
name|className
operator|+
literal|"_cp.html#cp"
operator|+
name|indice
operator|+
literal|"\" TARGET=\"ConstantPool\">Exception class index("
operator|+
name|indice
operator|+
literal|")</A>\n"
argument_list|)
expr_stmt|;
block|}
name|printWriter
operator|.
name|print
argument_list|(
literal|"</UL>\n"
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ATTR_LINE_NUMBER_TABLE
case|:
specifier|final
name|LineNumber
index|[]
name|line_numbers
init|=
operator|(
operator|(
name|LineNumberTable
operator|)
name|attribute
operator|)
operator|.
name|getLineNumberTable
argument_list|()
decl_stmt|;
comment|// List line number pairs
name|printWriter
operator|.
name|print
argument_list|(
literal|"<P>"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|line_numbers
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"("
operator|+
name|line_numbers
index|[
name|i
index|]
operator|.
name|getStartPC
argument_list|()
operator|+
literal|",&nbsp;"
operator|+
name|line_numbers
index|[
name|i
index|]
operator|.
name|getLineNumber
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|line_numbers
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
comment|// breakable
block|}
block|}
break|break;
case|case
name|Const
operator|.
name|ATTR_LOCAL_VARIABLE_TABLE
case|:
specifier|final
name|LocalVariable
index|[]
name|vars
init|=
operator|(
operator|(
name|LocalVariableTable
operator|)
name|attribute
operator|)
operator|.
name|getLocalVariableTable
argument_list|()
decl_stmt|;
comment|// List name, range and type
name|printWriter
operator|.
name|print
argument_list|(
literal|"<UL>"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|LocalVariable
name|var
range|:
name|vars
control|)
block|{
name|index
operator|=
name|var
operator|.
name|getSignatureIndex
argument_list|()
expr_stmt|;
name|String
name|signature
init|=
operator|(
operator|(
name|ConstantUtf8
operator|)
name|constantPool
operator|.
name|getConstant
argument_list|(
name|index
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
decl_stmt|;
name|signature
operator|=
name|Utility
operator|.
name|signatureToString
argument_list|(
name|signature
argument_list|,
literal|false
argument_list|)
expr_stmt|;
specifier|final
name|int
name|start
init|=
name|var
operator|.
name|getStartPC
argument_list|()
decl_stmt|;
specifier|final
name|int
name|end
init|=
name|start
operator|+
name|var
operator|.
name|getLength
argument_list|()
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"<LI>"
operator|+
name|Class2HTML
operator|.
name|referenceType
argument_list|(
name|signature
argument_list|)
operator|+
literal|"&nbsp;<B>"
operator|+
name|var
operator|.
name|getName
argument_list|()
operator|+
literal|"</B> in slot %"
operator|+
name|var
operator|.
name|getIndex
argument_list|()
operator|+
literal|"<BR>Valid from lines "
operator|+
literal|"<A HREF=\""
operator|+
name|className
operator|+
literal|"_code.html#code"
operator|+
name|method_number
operator|+
literal|"@"
operator|+
name|start
operator|+
literal|"\" TARGET=Code>"
operator|+
name|start
operator|+
literal|"</A> to "
operator|+
literal|"<A HREF=\""
operator|+
name|className
operator|+
literal|"_code.html#code"
operator|+
name|method_number
operator|+
literal|"@"
operator|+
name|end
operator|+
literal|"\" TARGET=Code>"
operator|+
name|end
operator|+
literal|"</A></LI>"
argument_list|)
expr_stmt|;
block|}
name|printWriter
operator|.
name|print
argument_list|(
literal|"</UL>\n"
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ATTR_INNER_CLASSES
case|:
specifier|final
name|InnerClass
index|[]
name|classes
init|=
operator|(
operator|(
name|InnerClasses
operator|)
name|attribute
operator|)
operator|.
name|getInnerClasses
argument_list|()
decl_stmt|;
comment|// List inner classes
name|printWriter
operator|.
name|print
argument_list|(
literal|"<UL>"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|InnerClass
name|classe
range|:
name|classes
control|)
block|{
specifier|final
name|String
name|name
decl_stmt|;
specifier|final
name|String
name|access
decl_stmt|;
name|index
operator|=
name|classe
operator|.
name|getInnerNameIndex
argument_list|()
expr_stmt|;
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
name|name
operator|=
operator|(
operator|(
name|ConstantUtf8
operator|)
name|constantPool
operator|.
name|getConstant
argument_list|(
name|index
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|name
operator|=
literal|"&lt;anonymous&gt;"
expr_stmt|;
block|}
name|access
operator|=
name|Utility
operator|.
name|accessToString
argument_list|(
name|classe
operator|.
name|getInnerAccessFlags
argument_list|()
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|print
argument_list|(
literal|"<LI><FONT COLOR=\"#FF0000\">"
operator|+
name|access
operator|+
literal|"</FONT> "
operator|+
name|constantHtml
operator|.
name|referenceConstant
argument_list|(
name|classe
operator|.
name|getInnerClassIndex
argument_list|()
argument_list|)
operator|+
literal|" in&nbsp;class "
operator|+
name|constantHtml
operator|.
name|referenceConstant
argument_list|(
name|classe
operator|.
name|getOuterClassIndex
argument_list|()
argument_list|)
operator|+
literal|" named "
operator|+
name|name
operator|+
literal|"</LI>\n"
argument_list|)
expr_stmt|;
block|}
name|printWriter
operator|.
name|print
argument_list|(
literal|"</UL>\n"
argument_list|)
expr_stmt|;
break|break;
default|default:
comment|// Such as Unknown attribute or Deprecated
name|printWriter
operator|.
name|print
argument_list|(
literal|"<P>"
operator|+
name|attribute
argument_list|)
expr_stmt|;
block|}
name|printWriter
operator|.
name|println
argument_list|(
literal|"</TD></TR>"
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

