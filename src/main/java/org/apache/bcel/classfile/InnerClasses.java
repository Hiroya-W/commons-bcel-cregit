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
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
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
comment|/**  * This class is derived from<em>Attribute</em> and denotes that this class is an Inner class of another. to the source  * file of this class. It is instantiated from the<em>Attribute.readAttribute()</em> method.  *  * @see Attribute  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|InnerClasses
extends|extends
name|Attribute
implements|implements
name|Iterable
argument_list|<
name|InnerClass
argument_list|>
block|{
comment|/**      * Empty array.      */
specifier|private
specifier|static
specifier|final
name|InnerClass
index|[]
name|EMPTY_INNER_CLASSE_ARRAY
init|=
block|{}
decl_stmt|;
specifier|private
name|InnerClass
index|[]
name|innerClasses
decl_stmt|;
comment|/**      * Initialize from another object. Note that both objects use the same references (shallow copy). Use clone() for a      * physical copy.      */
specifier|public
name|InnerClasses
parameter_list|(
specifier|final
name|InnerClasses
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
name|getInnerClasses
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from input stream.      *      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param input Input stream      * @param constant_pool Array of constants      * @throws IOException if an I/O error occurs.      */
name|InnerClasses
parameter_list|(
specifier|final
name|int
name|name_index
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|DataInput
name|input
parameter_list|,
specifier|final
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
operator|(
name|InnerClass
index|[]
operator|)
literal|null
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
specifier|final
name|int
name|number_of_classes
init|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|innerClasses
operator|=
operator|new
name|InnerClass
index|[
name|number_of_classes
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
name|number_of_classes
condition|;
name|i
operator|++
control|)
block|{
name|innerClasses
index|[
name|i
index|]
operator|=
operator|new
name|InnerClass
argument_list|(
name|input
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param innerClasses array of inner classes attributes      * @param constant_pool Array of constants      */
specifier|public
name|InnerClasses
parameter_list|(
specifier|final
name|int
name|name_index
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|InnerClass
index|[]
name|innerClasses
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|ATTR_INNER_CLASSES
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
name|innerClasses
operator|=
name|innerClasses
operator|!=
literal|null
condition|?
name|innerClasses
else|:
name|EMPTY_INNER_CLASSE_ARRAY
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.      * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
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
name|visitInnerClasses
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return deep copy of this attribute      */
annotation|@
name|Override
specifier|public
name|Attribute
name|copy
parameter_list|(
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
block|{
comment|// TODO this could be recoded to use a lower level constructor after creating a copy of the inner classes
specifier|final
name|InnerClasses
name|c
init|=
operator|(
name|InnerClasses
operator|)
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|innerClasses
operator|=
operator|new
name|InnerClass
index|[
name|innerClasses
operator|.
name|length
index|]
expr_stmt|;
name|Arrays
operator|.
name|setAll
argument_list|(
name|c
operator|.
name|innerClasses
argument_list|,
name|i
lambda|->
name|innerClasses
index|[
name|i
index|]
operator|.
name|copy
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setConstantPool
argument_list|(
name|constantPool
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
comment|/**      * Dump source file attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
annotation|@
name|Override
specifier|public
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
name|innerClasses
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|InnerClass
name|inner_class
range|:
name|innerClasses
control|)
block|{
name|inner_class
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return array of inner class "records"      */
specifier|public
name|InnerClass
index|[]
name|getInnerClasses
parameter_list|()
block|{
return|return
name|innerClasses
return|;
block|}
annotation|@
name|Override
specifier|public
name|Iterator
argument_list|<
name|InnerClass
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|Stream
operator|.
name|of
argument_list|(
name|innerClasses
argument_list|)
operator|.
name|iterator
argument_list|()
return|;
block|}
comment|/**      * @param innerClasses the array of inner classes      */
specifier|public
name|void
name|setInnerClasses
parameter_list|(
specifier|final
name|InnerClass
index|[]
name|innerClasses
parameter_list|)
block|{
name|this
operator|.
name|innerClasses
operator|=
name|innerClasses
operator|!=
literal|null
condition|?
name|innerClasses
else|:
name|EMPTY_INNER_CLASSE_ARRAY
expr_stmt|;
block|}
comment|/**      * @return String representation.      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"InnerClasses("
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|innerClasses
operator|.
name|length
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"):\n"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|InnerClass
name|inner_class
range|:
name|innerClasses
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|inner_class
operator|.
name|toString
argument_list|(
name|super
operator|.
name|getConstantPool
argument_list|()
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
block|}
end_class

end_unit

