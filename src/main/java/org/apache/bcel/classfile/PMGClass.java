begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
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
name|Constants
import|;
end_import

begin_comment
comment|/**  * This class is derived from<em>Attribute</em> and represents a reference  * to a PMG attribute.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  * @see     Attribute  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|PMGClass
extends|extends
name|Attribute
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|7075964153234211509L
decl_stmt|;
specifier|private
name|int
name|pmg_class_index
decl_stmt|,
name|pmg_index
decl_stmt|;
comment|/**      * Initialize from another object. Note that both objects use the same      * references (shallow copy). Use clone() for a physical copy.      */
specifier|public
name|PMGClass
parameter_list|(
name|PMGClass
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getLength
argument_list|()
argument_list|,
name|c
operator|.
name|getPMGIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getPMGClassIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from file stream.      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param file Input stream      * @param constant_pool Array of constants      * @throws IOException      */
name|PMGClass
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|DataInput
name|file
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|name_index
argument_list|,
name|length
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param pmg_index index in constant pool for source file name      * @param pmg_class_index Index in constant pool to CONSTANT_Utf8      * @param constant_pool Array of constants      */
specifier|public
name|PMGClass
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|int
name|pmg_index
parameter_list|,
name|int
name|pmg_class_index
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|ATTR_PMG
argument_list|,
name|name_index
argument_list|,
name|length
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
name|this
operator|.
name|pmg_index
operator|=
name|pmg_index
expr_stmt|;
name|this
operator|.
name|pmg_class_index
operator|=
name|pmg_class_index
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
specifier|public
name|void
name|accept
parameter_list|(
name|Visitor
name|v
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Visiting non-standard PMGClass object"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump source file attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
specifier|public
specifier|final
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|pmg_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|pmg_class_index
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Index in constant pool of source file name.      */
specifier|public
specifier|final
name|int
name|getPMGClassIndex
parameter_list|()
block|{
return|return
name|pmg_class_index
return|;
block|}
comment|/**      * @param pmg_class_index      */
specifier|public
specifier|final
name|void
name|setPMGClassIndex
parameter_list|(
name|int
name|pmg_class_index
parameter_list|)
block|{
name|this
operator|.
name|pmg_class_index
operator|=
name|pmg_class_index
expr_stmt|;
block|}
comment|/**      * @return Index in constant pool of source file name.      */
specifier|public
specifier|final
name|int
name|getPMGIndex
parameter_list|()
block|{
return|return
name|pmg_index
return|;
block|}
comment|/**      * @param pmg_index      */
specifier|public
specifier|final
name|void
name|setPMGIndex
parameter_list|(
name|int
name|pmg_index
parameter_list|)
block|{
name|this
operator|.
name|pmg_index
operator|=
name|pmg_index
expr_stmt|;
block|}
comment|/**      * @return PMG name.      */
specifier|public
specifier|final
name|String
name|getPMGName
parameter_list|()
block|{
name|ConstantUtf8
name|c
init|=
operator|(
name|ConstantUtf8
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|pmg_index
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
decl_stmt|;
return|return
name|c
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return PMG class name.      */
specifier|public
specifier|final
name|String
name|getPMGClassName
parameter_list|()
block|{
name|ConstantUtf8
name|c
init|=
operator|(
name|ConstantUtf8
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|pmg_class_index
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
decl_stmt|;
return|return
name|c
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return String representation      */
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"PMGClass("
operator|+
name|getPMGName
argument_list|()
operator|+
literal|", "
operator|+
name|getPMGClassName
argument_list|()
operator|+
literal|")"
return|;
block|}
comment|/**      * @return deep copy of this attribute      */
specifier|public
name|Attribute
name|copy
parameter_list|(
name|ConstantPool
name|_constant_pool
parameter_list|)
block|{
return|return
operator|(
name|PMGClass
operator|)
name|clone
argument_list|()
return|;
block|}
block|}
end_class

end_unit

