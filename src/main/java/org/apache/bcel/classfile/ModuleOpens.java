begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
import|;
end_import

begin_comment
comment|/**  * This class represents an entry in the opens table of the Module attribute.  * Each entry describes a package which the parent module opens.  *  * @see   Module  * @since 6.4.0  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ModuleOpens
implements|implements
name|Cloneable
implements|,
name|Node
block|{
specifier|private
name|int
name|opens_index
decl_stmt|;
comment|// points to CONSTANT_Package_info
specifier|private
name|int
name|opens_flags
decl_stmt|;
specifier|private
name|int
name|opens_to_count
decl_stmt|;
specifier|private
name|int
name|opens_to_index
index|[]
decl_stmt|;
comment|// points to CONSTANT_Module_info
comment|/**      * Construct object from file stream.      *      * @param file Input stream      * @throws IOException if an I/O Exception occurs in readUnsignedShort      */
name|ModuleOpens
parameter_list|(
specifier|final
name|DataInput
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|opens_index
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|opens_flags
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|opens_to_count
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|opens_to_index
operator|=
operator|new
name|int
index|[
name|opens_to_count
index|]
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
name|opens_to_count
condition|;
name|i
operator|++
control|)
block|{
name|opens_to_index
index|[
name|i
index|]
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
specifier|final
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitModuleOpens
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|// TODO add more getters and setters?
comment|/**      * Dump table entry to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O Exception occurs in writeShort      */
specifier|public
specifier|final
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|file
operator|.
name|writeShort
argument_list|(
name|opens_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|opens_flags
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|opens_to_count
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|int
name|entry
range|:
name|opens_to_index
control|)
block|{
name|file
operator|.
name|writeShort
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return String representation      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"opens("
operator|+
name|opens_index
operator|+
literal|", "
operator|+
name|opens_flags
operator|+
literal|", "
operator|+
name|opens_to_count
operator|+
literal|", ...)"
return|;
block|}
comment|/**      * @return Resolved string representation      */
specifier|public
specifier|final
name|String
name|toString
parameter_list|(
specifier|final
name|ConstantPool
name|constant_pool
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
specifier|final
name|String
name|package_name
init|=
name|constant_pool
operator|.
name|constantToString
argument_list|(
name|opens_index
argument_list|,
name|Const
operator|.
name|CONSTANT_Package
argument_list|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|Utility
operator|.
name|compactClassName
argument_list|(
name|package_name
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%04x"
argument_list|,
name|opens_flags
argument_list|)
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|", to("
argument_list|)
operator|.
name|append
argument_list|(
name|opens_to_count
argument_list|)
operator|.
name|append
argument_list|(
literal|"):\n"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|int
name|index
range|:
name|opens_to_index
control|)
block|{
specifier|final
name|String
name|module_name
init|=
name|constant_pool
operator|.
name|getConstantString
argument_list|(
name|index
argument_list|,
name|Const
operator|.
name|CONSTANT_Module
argument_list|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"      "
argument_list|)
operator|.
name|append
argument_list|(
name|Utility
operator|.
name|compactClassName
argument_list|(
name|module_name
argument_list|,
literal|false
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
return|return
name|buf
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|buf
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
return|;
comment|// remove the last newline
block|}
comment|/**      * @return deep copy of this object      */
specifier|public
name|ModuleOpens
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|ModuleOpens
operator|)
name|clone
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
comment|// TODO should this throw?
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

