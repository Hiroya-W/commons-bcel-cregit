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
comment|/**   * Abstract super class for Fieldref and Methodref constants.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  * @see     ConstantFieldref  * @see     ConstantMethodref  * @see     ConstantInterfaceMethodref  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ConstantCP
extends|extends
name|Constant
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|6275762995206209402L
decl_stmt|;
comment|/** References to the constants containing the class and the field signature      */
specifier|protected
name|int
name|class_index
decl_stmt|,
name|name_and_type_index
decl_stmt|;
comment|/**      * Initialize from another object.      */
specifier|public
name|ConstantCP
parameter_list|(
name|ConstantCP
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getTag
argument_list|()
argument_list|,
name|c
operator|.
name|getClassIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialize instance from file data.      *      * @param tag  Constant type tag      * @param file Input stream      * @throws IOException      */
name|ConstantCP
parameter_list|(
name|byte
name|tag
parameter_list|,
name|DataInput
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|tag
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
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param class_index Reference to the class containing the field      * @param name_and_type_index and the field signature      */
specifier|protected
name|ConstantCP
parameter_list|(
name|byte
name|tag
parameter_list|,
name|int
name|class_index
parameter_list|,
name|int
name|name_and_type_index
parameter_list|)
block|{
name|super
argument_list|(
name|tag
argument_list|)
expr_stmt|;
name|this
operator|.
name|class_index
operator|=
name|class_index
expr_stmt|;
name|this
operator|.
name|name_and_type_index
operator|=
name|name_and_type_index
expr_stmt|;
block|}
comment|/**       * Dump constant field reference to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
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
name|file
operator|.
name|writeByte
argument_list|(
name|tag
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|class_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|name_and_type_index
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Reference (index) to class this field or method belongs to.      */
specifier|public
specifier|final
name|int
name|getClassIndex
parameter_list|()
block|{
return|return
name|class_index
return|;
block|}
comment|/**      * @return Reference (index) to signature of the field.      */
specifier|public
specifier|final
name|int
name|getNameAndTypeIndex
parameter_list|()
block|{
return|return
name|name_and_type_index
return|;
block|}
comment|/**      * @param class_index points to Constant_class       */
specifier|public
specifier|final
name|void
name|setClassIndex
parameter_list|(
name|int
name|class_index
parameter_list|)
block|{
name|this
operator|.
name|class_index
operator|=
name|class_index
expr_stmt|;
block|}
comment|/**      * @return Class this field belongs to.      */
specifier|public
name|String
name|getClass
parameter_list|(
name|ConstantPool
name|cp
parameter_list|)
block|{
return|return
name|cp
operator|.
name|constantToString
argument_list|(
name|class_index
argument_list|,
name|Constants
operator|.
name|CONSTANT_Class
argument_list|)
return|;
block|}
comment|/**      * @param name_and_type_index points to Constant_NameAndType      */
specifier|public
specifier|final
name|void
name|setNameAndTypeIndex
parameter_list|(
name|int
name|name_and_type_index
parameter_list|)
block|{
name|this
operator|.
name|name_and_type_index
operator|=
name|name_and_type_index
expr_stmt|;
block|}
comment|/**      * @return String representation.      */
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
operator|+
literal|"(class_index = "
operator|+
name|class_index
operator|+
literal|", name_and_type_index = "
operator|+
name|name_and_type_index
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

