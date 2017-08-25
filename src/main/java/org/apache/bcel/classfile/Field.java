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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
operator|.
name|Type
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
name|util
operator|.
name|BCELComparator
import|;
end_import

begin_comment
comment|/**  * This class represents the field info structure, i.e., the representation  * for a variable in the class. See JVM specification for details.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Field
extends|extends
name|FieldOrMethod
block|{
specifier|private
specifier|static
name|BCELComparator
name|bcelComparator
init|=
operator|new
name|BCELComparator
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
specifier|final
name|Object
name|o1
parameter_list|,
specifier|final
name|Object
name|o2
parameter_list|)
block|{
specifier|final
name|Field
name|THIS
init|=
operator|(
name|Field
operator|)
name|o1
decl_stmt|;
specifier|final
name|Field
name|THAT
init|=
operator|(
name|Field
operator|)
name|o2
decl_stmt|;
return|return
name|THIS
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|THAT
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|THIS
operator|.
name|getSignature
argument_list|()
operator|.
name|equals
argument_list|(
name|THAT
operator|.
name|getSignature
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|(
specifier|final
name|Object
name|o
parameter_list|)
block|{
specifier|final
name|Field
name|THIS
init|=
operator|(
name|Field
operator|)
name|o
decl_stmt|;
return|return
name|THIS
operator|.
name|getSignature
argument_list|()
operator|.
name|hashCode
argument_list|()
operator|^
name|THIS
operator|.
name|getName
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Initialize from another object. Note that both objects use the same      * references (shallow copy). Use clone() for a physical copy.      */
specifier|public
name|Field
parameter_list|(
specifier|final
name|Field
name|c
parameter_list|)
block|{
name|super
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from file stream.      * @param file Input stream      */
name|Field
parameter_list|(
specifier|final
name|DataInput
name|file
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|super
argument_list|(
name|file
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param access_flags Access rights of field      * @param name_index Points to field name in constant pool      * @param signature_index Points to encoded signature      * @param attributes Collection of attributes      * @param constant_pool Array of constants      */
specifier|public
name|Field
parameter_list|(
specifier|final
name|int
name|access_flags
parameter_list|,
specifier|final
name|int
name|name_index
parameter_list|,
specifier|final
name|int
name|signature_index
parameter_list|,
specifier|final
name|Attribute
index|[]
name|attributes
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|access_flags
argument_list|,
name|name_index
argument_list|,
name|signature_index
argument_list|,
name|attributes
argument_list|,
name|constant_pool
argument_list|)
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
name|visitField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return constant value associated with this field (may be null)      */
specifier|public
specifier|final
name|ConstantValue
name|getConstantValue
parameter_list|()
block|{
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|super
operator|.
name|getAttributes
argument_list|()
control|)
block|{
if|if
condition|(
name|attribute
operator|.
name|getTag
argument_list|()
operator|==
name|Const
operator|.
name|ATTR_CONSTANT_VALUE
condition|)
block|{
return|return
operator|(
name|ConstantValue
operator|)
name|attribute
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Return string representation close to declaration format,      * `public static final short MAX = 100', e.g..      *      * @return String representation of field, including the signature.      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
name|String
name|name
decl_stmt|;
name|String
name|signature
decl_stmt|;
name|String
name|access
decl_stmt|;
comment|// Short cuts to constant pool
comment|// Get names from constant pool
name|access
operator|=
name|Utility
operator|.
name|accessToString
argument_list|(
name|super
operator|.
name|getAccessFlags
argument_list|()
argument_list|)
expr_stmt|;
name|access
operator|=
name|access
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
operator|(
name|access
operator|+
literal|" "
operator|)
expr_stmt|;
name|signature
operator|=
name|Utility
operator|.
name|signatureToString
argument_list|(
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|name
operator|=
name|getName
argument_list|()
expr_stmt|;
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|(
literal|64
argument_list|)
decl_stmt|;
comment|// CHECKSTYLE IGNORE MagicNumber
name|buf
operator|.
name|append
argument_list|(
name|access
argument_list|)
operator|.
name|append
argument_list|(
name|signature
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
specifier|final
name|ConstantValue
name|cv
init|=
name|getConstantValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|cv
operator|!=
literal|null
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|" = "
argument_list|)
operator|.
name|append
argument_list|(
name|cv
argument_list|)
expr_stmt|;
block|}
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|super
operator|.
name|getAttributes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
operator|(
name|attribute
operator|instanceof
name|ConstantValue
operator|)
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|" ["
argument_list|)
operator|.
name|append
argument_list|(
name|attribute
argument_list|)
operator|.
name|append
argument_list|(
literal|"]"
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
comment|/**      * @return deep copy of this field      */
specifier|public
specifier|final
name|Field
name|copy
parameter_list|(
specifier|final
name|ConstantPool
name|_constant_pool
parameter_list|)
block|{
return|return
operator|(
name|Field
operator|)
name|copy_
argument_list|(
name|_constant_pool
argument_list|)
return|;
block|}
comment|/**      * @return type of field      */
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|Type
operator|.
name|getReturnType
argument_list|(
name|getSignature
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * @return Comparison strategy object      */
specifier|public
specifier|static
name|BCELComparator
name|getComparator
parameter_list|()
block|{
return|return
name|bcelComparator
return|;
block|}
comment|/**      * @param comparator Comparison strategy object      */
specifier|public
specifier|static
name|void
name|setComparator
parameter_list|(
specifier|final
name|BCELComparator
name|comparator
parameter_list|)
block|{
name|bcelComparator
operator|=
name|comparator
expr_stmt|;
block|}
comment|/**      * Return value as defined by given BCELComparator strategy.      * By default two Field objects are said to be equal when      * their names and signatures are equal.      *      * @see java.lang.Object#equals(java.lang.Object)      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
specifier|final
name|Object
name|obj
parameter_list|)
block|{
return|return
name|bcelComparator
operator|.
name|equals
argument_list|(
name|this
argument_list|,
name|obj
argument_list|)
return|;
block|}
comment|/**      * Return value as defined by given BCELComparator strategy.      * By default return the hashcode of the field's name XOR signature.      *      * @see java.lang.Object#hashCode()      */
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|bcelComparator
operator|.
name|hashCode
argument_list|(
name|this
argument_list|)
return|;
block|}
block|}
end_class

end_unit

