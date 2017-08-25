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
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
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
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|Constants
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
name|ClassParser
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
name|JavaClass
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
name|Method
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
comment|/**  * Read class file(s) and convert them into HTML files.  *  * Given a JavaClass object "class" that is in package "package" five files  * will be created in the specified directory.  *  *<OL>  *<LI> "package"."class".html as the main file which defines the frames for  * the following subfiles.  *<LI>  "package"."class"_attributes.html contains all (known) attributes found in the file  *<LI>  "package"."class"_cp.html contains the constant pool  *<LI>  "package"."class"_code.html contains the byte code  *<LI>  "package"."class"_methods.html contains references to all methods and fields of the class  *</OL>  *  * All subfiles reference each other appropriately, e.g. clicking on a  * method in the Method's frame will jump to the appropriate method in  * the Code frame.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|Class2HTML
implements|implements
name|Constants
block|{
specifier|private
specifier|final
name|JavaClass
name|java_class
decl_stmt|;
comment|// current class object
specifier|private
specifier|final
name|String
name|dir
decl_stmt|;
specifier|private
specifier|static
name|String
name|class_package
decl_stmt|;
comment|// name of package, unclean to make it static, but ...
specifier|private
specifier|static
name|String
name|class_name
decl_stmt|;
comment|// name of current class, dito
specifier|private
specifier|static
name|ConstantPool
name|constant_pool
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|basic_types
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
name|basic_types
operator|.
name|add
argument_list|(
literal|"int"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"short"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"boolean"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"void"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"char"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"byte"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"long"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"double"
argument_list|)
expr_stmt|;
name|basic_types
operator|.
name|add
argument_list|(
literal|"float"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write contents of the given JavaClass into HTML files.      *      * @param java_class The class to write      * @param dir The directory to put the files in      */
specifier|public
name|Class2HTML
parameter_list|(
specifier|final
name|JavaClass
name|java_class
parameter_list|,
specifier|final
name|String
name|dir
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|Method
index|[]
name|methods
init|=
name|java_class
operator|.
name|getMethods
argument_list|()
decl_stmt|;
name|this
operator|.
name|java_class
operator|=
name|java_class
expr_stmt|;
name|this
operator|.
name|dir
operator|=
name|dir
expr_stmt|;
name|class_name
operator|=
name|java_class
operator|.
name|getClassName
argument_list|()
expr_stmt|;
comment|// Remember full name
name|constant_pool
operator|=
name|java_class
operator|.
name|getConstantPool
argument_list|()
expr_stmt|;
comment|// Get package name by tacking off everything after the last `.'
specifier|final
name|int
name|index
init|=
name|class_name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
operator|-
literal|1
condition|)
block|{
name|class_package
operator|=
name|class_name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|class_package
operator|=
literal|""
expr_stmt|;
comment|// default package
block|}
specifier|final
name|ConstantHTML
name|constant_html
init|=
operator|new
name|ConstantHTML
argument_list|(
name|dir
argument_list|,
name|class_name
argument_list|,
name|class_package
argument_list|,
name|methods
argument_list|,
name|constant_pool
argument_list|)
decl_stmt|;
comment|/* Attributes can't be written in one step, so we just open a file          * which will be written consequently.          */
specifier|final
name|AttributeHTML
name|attribute_html
init|=
operator|new
name|AttributeHTML
argument_list|(
name|dir
argument_list|,
name|class_name
argument_list|,
name|constant_pool
argument_list|,
name|constant_html
argument_list|)
decl_stmt|;
operator|new
name|MethodHTML
argument_list|(
name|dir
argument_list|,
name|class_name
argument_list|,
name|methods
argument_list|,
name|java_class
operator|.
name|getFields
argument_list|()
argument_list|,
name|constant_html
argument_list|,
name|attribute_html
argument_list|)
expr_stmt|;
comment|// Write main file (with frames, yuk)
name|writeMainHTML
argument_list|(
name|attribute_html
argument_list|)
expr_stmt|;
operator|new
name|CodeHTML
argument_list|(
name|dir
argument_list|,
name|class_name
argument_list|,
name|methods
argument_list|,
name|constant_pool
argument_list|,
name|constant_html
argument_list|)
expr_stmt|;
name|attribute_html
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|argv
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|String
index|[]
name|file_name
init|=
operator|new
name|String
index|[
name|argv
operator|.
name|length
index|]
decl_stmt|;
name|int
name|files
init|=
literal|0
decl_stmt|;
name|ClassParser
name|parser
init|=
literal|null
decl_stmt|;
name|JavaClass
name|java_class
init|=
literal|null
decl_stmt|;
name|String
name|zip_file
init|=
literal|null
decl_stmt|;
specifier|final
name|char
name|sep
init|=
name|File
operator|.
name|separatorChar
decl_stmt|;
name|String
name|dir
init|=
literal|"."
operator|+
name|sep
decl_stmt|;
comment|// Where to store HTML files
comment|/* Parse command line arguments.          */
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|argv
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'-'
condition|)
block|{
comment|// command line switch
if|if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-d"
argument_list|)
condition|)
block|{
comment|// Specify target directory, default '.'
name|dir
operator|=
name|argv
index|[
operator|++
name|i
index|]
expr_stmt|;
if|if
condition|(
operator|!
name|dir
operator|.
name|endsWith
argument_list|(
literal|""
operator|+
name|sep
argument_list|)
condition|)
block|{
name|dir
operator|=
name|dir
operator|+
name|sep
expr_stmt|;
block|}
specifier|final
name|File
name|store
init|=
operator|new
name|File
argument_list|(
name|dir
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|store
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
specifier|final
name|boolean
name|created
init|=
name|store
operator|.
name|mkdirs
argument_list|()
decl_stmt|;
comment|// Create target directory if necessary
if|if
condition|(
operator|!
name|created
condition|)
block|{
if|if
condition|(
operator|!
name|store
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Tried to create the directory "
operator|+
name|dir
operator|+
literal|" but failed"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-zip"
argument_list|)
condition|)
block|{
name|zip_file
operator|=
name|argv
index|[
operator|++
name|i
index|]
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Unknown option "
operator|+
name|argv
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|file_name
index|[
name|files
operator|++
index|]
operator|=
name|argv
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
if|if
condition|(
name|files
operator|==
literal|0
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Class2HTML: No input files specified."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Loop through files ...
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|files
condition|;
name|i
operator|++
control|)
block|{
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"Processing "
operator|+
name|file_name
index|[
name|i
index|]
operator|+
literal|"..."
argument_list|)
expr_stmt|;
if|if
condition|(
name|zip_file
operator|==
literal|null
condition|)
block|{
name|parser
operator|=
operator|new
name|ClassParser
argument_list|(
name|file_name
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// Create parser object from file
block|}
else|else
block|{
name|parser
operator|=
operator|new
name|ClassParser
argument_list|(
name|zip_file
argument_list|,
name|file_name
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// Create parser object from zip file
block|}
name|java_class
operator|=
name|parser
operator|.
name|parse
argument_list|()
expr_stmt|;
operator|new
name|Class2HTML
argument_list|(
name|java_class
argument_list|,
name|dir
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Done."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Utility method that converts a class reference in the constant pool,      * i.e., an index to a string.      */
specifier|static
name|String
name|referenceClass
parameter_list|(
specifier|final
name|int
name|index
parameter_list|)
block|{
name|String
name|str
init|=
name|constant_pool
operator|.
name|getConstantString
argument_list|(
name|index
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
decl_stmt|;
name|str
operator|=
name|Utility
operator|.
name|compactClassName
argument_list|(
name|str
argument_list|)
expr_stmt|;
name|str
operator|=
name|Utility
operator|.
name|compactClassName
argument_list|(
name|str
argument_list|,
name|class_package
operator|+
literal|"."
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
literal|"<A HREF=\""
operator|+
name|class_name
operator|+
literal|"_cp.html#cp"
operator|+
name|index
operator|+
literal|"\" TARGET=ConstantPool>"
operator|+
name|str
operator|+
literal|"</A>"
return|;
block|}
specifier|static
name|String
name|referenceType
parameter_list|(
specifier|final
name|String
name|type
parameter_list|)
block|{
name|String
name|short_type
init|=
name|Utility
operator|.
name|compactClassName
argument_list|(
name|type
argument_list|)
decl_stmt|;
name|short_type
operator|=
name|Utility
operator|.
name|compactClassName
argument_list|(
name|short_type
argument_list|,
name|class_package
operator|+
literal|"."
argument_list|,
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|int
name|index
init|=
name|type
operator|.
name|indexOf
argument_list|(
literal|'['
argument_list|)
decl_stmt|;
comment|// Type is an array?
name|String
name|base_type
init|=
name|type
decl_stmt|;
if|if
condition|(
name|index
operator|>
operator|-
literal|1
condition|)
block|{
name|base_type
operator|=
name|type
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
expr_stmt|;
comment|// Tack of the `['
block|}
comment|// test for basic type
if|if
condition|(
name|basic_types
operator|.
name|contains
argument_list|(
name|base_type
argument_list|)
condition|)
block|{
return|return
literal|"<FONT COLOR=\"#00FF00\">"
operator|+
name|type
operator|+
literal|"</FONT>"
return|;
block|}
return|return
literal|"<A HREF=\""
operator|+
name|base_type
operator|+
literal|".html\" TARGET=_top>"
operator|+
name|short_type
operator|+
literal|"</A>"
return|;
block|}
specifier|static
name|String
name|toHTML
parameter_list|(
specifier|final
name|String
name|str
parameter_list|)
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|str
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|ch
decl_stmt|;
switch|switch
condition|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
condition|)
block|{
case|case
literal|'<'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"&lt;"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'>'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"&gt;"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\n'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"\\n"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\r'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"\\r"
argument_list|)
expr_stmt|;
break|break;
default|default:
name|buf
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|void
name|writeMainHTML
parameter_list|(
specifier|final
name|AttributeHTML
name|attribute_html
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|PrintWriter
name|file
init|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|dir
operator|+
name|class_name
operator|+
literal|".html"
argument_list|)
argument_list|)
init|)
block|{
name|file
operator|.
name|println
argument_list|(
literal|"<HTML>\n"
operator|+
literal|"<HEAD><TITLE>Documentation for "
operator|+
name|class_name
operator|+
literal|"</TITLE>"
operator|+
literal|"</HEAD>\n"
operator|+
literal|"<FRAMESET BORDER=1 cols=\"30%,*\">\n"
operator|+
literal|"<FRAMESET BORDER=1 rows=\"80%,*\">\n"
operator|+
literal|"<FRAME NAME=\"ConstantPool\" SRC=\""
operator|+
name|class_name
operator|+
literal|"_cp.html"
operator|+
literal|"\"\n MARGINWIDTH=\"0\" "
operator|+
literal|"MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n"
operator|+
literal|"<FRAME NAME=\"Attributes\" SRC=\""
operator|+
name|class_name
operator|+
literal|"_attributes.html"
operator|+
literal|"\"\n MARGINWIDTH=\"0\" "
operator|+
literal|"MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n"
operator|+
literal|"</FRAMESET>\n"
operator|+
literal|"<FRAMESET BORDER=1 rows=\"80%,*\">\n"
operator|+
literal|"<FRAME NAME=\"Code\" SRC=\""
operator|+
name|class_name
operator|+
literal|"_code.html\"\n MARGINWIDTH=0 "
operator|+
literal|"MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n"
operator|+
literal|"<FRAME NAME=\"Methods\" SRC=\""
operator|+
name|class_name
operator|+
literal|"_methods.html\"\n MARGINWIDTH=0 "
operator|+
literal|"MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n"
operator|+
literal|"</FRAMESET></FRAMESET></HTML>"
argument_list|)
expr_stmt|;
block|}
specifier|final
name|Attribute
index|[]
name|attributes
init|=
name|java_class
operator|.
name|getAttributes
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|attributes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|attribute_html
operator|.
name|writeAttribute
argument_list|(
name|attributes
index|[
name|i
index|]
argument_list|,
literal|"class"
operator|+
name|i
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

