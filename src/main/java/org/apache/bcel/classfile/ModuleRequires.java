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
comment|/**  * This class represents an entry in the requires table of the Module attribute.  * Each entry describes a module on which the parent module depends.  *  * @see   Module  * @since 6.4.0  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ModuleRequires
implements|implements
name|Cloneable
implements|,
name|Node
block|{
specifier|private
specifier|final
name|int
name|requires_index
decl_stmt|;
comment|// points to CONSTANT_Module_info
specifier|private
specifier|final
name|int
name|requires_flags
decl_stmt|;
specifier|private
specifier|final
name|int
name|requires_version_index
decl_stmt|;
comment|// either 0 or points to CONSTANT_Utf8_info
comment|/**      * Construct object from file stream.      *      * @param file Input stream      * @throws IOException if an I/O Exception occurs in readUnsignedShort      */
name|ModuleRequires
parameter_list|(
specifier|final
name|DataInput
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|requires_index
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|requires_flags
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|requires_version_index
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
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
name|visitModuleRequires
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
name|requires_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|requires_flags
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|requires_version_index
argument_list|)
expr_stmt|;
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
literal|"requires("
operator|+
name|requires_index
operator|+
literal|", "
operator|+
name|String
operator|.
name|format
argument_list|(
literal|"%04x"
argument_list|,
name|requires_flags
argument_list|)
operator|+
literal|", "
operator|+
name|requires_version_index
operator|+
literal|")"
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
name|module_name
init|=
name|constant_pool
operator|.
name|constantToString
argument_list|(
name|requires_index
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
name|Utility
operator|.
name|compactClassName
argument_list|(
name|module_name
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
name|requires_flags
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|String
name|version
init|=
operator|(
name|requires_version_index
operator|==
literal|0
condition|?
literal|"0"
else|:
name|constant_pool
operator|.
name|getConstantString
argument_list|(
name|requires_version_index
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
operator|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|version
argument_list|)
expr_stmt|;
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * @return deep copy of this object      */
specifier|public
name|ModuleRequires
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|ModuleRequires
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

