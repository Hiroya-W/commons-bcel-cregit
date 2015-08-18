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
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
operator|.
name|util
operator|.
name|BCELComparator
import|;
end_import

begin_comment
comment|/**  * This class represents the method info structure, i.e., the representation   * for a method in the class. See JVM specification for details.  * A method has access flags, a name, a signature and a number of attributes.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Method
extends|extends
name|FieldOrMethod
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|2013983967283787941L
decl_stmt|;
specifier|private
specifier|static
name|BCELComparator
name|_cmp
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
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|Method
name|THIS
init|=
operator|(
name|Method
operator|)
name|o1
decl_stmt|;
name|Method
name|THAT
init|=
operator|(
name|Method
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
name|Object
name|o
parameter_list|)
block|{
name|Method
name|THIS
init|=
operator|(
name|Method
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
comment|// annotations defined on the parameters of a method
specifier|private
name|ParameterAnnotationEntry
index|[]
name|parameterAnnotationEntries
decl_stmt|;
comment|/**      * Empty constructor, all attributes have to be defined via `setXXX'      * methods. Use at your own risk.      */
specifier|public
name|Method
parameter_list|()
block|{
block|}
comment|/**      * Initialize from another object. Note that both objects use the same      * references (shallow copy). Use clone() for a physical copy.      */
specifier|public
name|Method
parameter_list|(
name|Method
name|c
parameter_list|)
block|{
name|super
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from file stream.      * @param file Input stream      * @throws IOException      * @throws ClassFormatException      */
name|Method
parameter_list|(
name|DataInput
name|file
parameter_list|,
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
comment|/**      * @param access_flags Access rights of method      * @param name_index Points to field name in constant pool      * @param signature_index Points to encoded signature      * @param attributes Collection of attributes      * @param constant_pool Array of constants      */
specifier|public
name|Method
parameter_list|(
name|int
name|access_flags
parameter_list|,
name|int
name|name_index
parameter_list|,
name|int
name|signature_index
parameter_list|,
name|Attribute
index|[]
name|attributes
parameter_list|,
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
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitMethod
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Code attribute of method, if any      */
specifier|public
specifier|final
name|Code
name|getCode
parameter_list|()
block|{
for|for
control|(
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
operator|instanceof
name|Code
condition|)
block|{
return|return
operator|(
name|Code
operator|)
name|attribute
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * @return ExceptionTable attribute of method, if any, i.e., list all      * exceptions the method may throw not exception handlers!      */
specifier|public
specifier|final
name|ExceptionTable
name|getExceptionTable
parameter_list|()
block|{
for|for
control|(
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
operator|instanceof
name|ExceptionTable
condition|)
block|{
return|return
operator|(
name|ExceptionTable
operator|)
name|attribute
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/** @return LocalVariableTable of code attribute if any, i.e. the call is forwarded      * to the Code atribute.      */
specifier|public
specifier|final
name|LocalVariableTable
name|getLocalVariableTable
parameter_list|()
block|{
name|Code
name|code
init|=
name|getCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|code
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|code
operator|.
name|getLocalVariableTable
argument_list|()
return|;
block|}
comment|/** @return LineNumberTable of code attribute if any, i.e. the call is forwarded      * to the Code atribute.      */
specifier|public
specifier|final
name|LineNumberTable
name|getLineNumberTable
parameter_list|()
block|{
name|Code
name|code
init|=
name|getCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|code
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|code
operator|.
name|getLineNumberTable
argument_list|()
return|;
block|}
comment|/**      * Return string representation close to declaration format,      * `public static void main(String[] args) throws IOException', e.g.      *      * @return String representation of the method.      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
name|ConstantUtf8
name|c
decl_stmt|;
name|String
name|name
decl_stmt|,
name|signature
decl_stmt|,
name|access
decl_stmt|;
comment|// Short cuts to constant pool
name|StringBuilder
name|buf
decl_stmt|;
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
comment|// Get name and signature from constant pool
name|c
operator|=
operator|(
name|ConstantUtf8
operator|)
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getSignatureIndex
argument_list|()
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|signature
operator|=
name|c
operator|.
name|getBytes
argument_list|()
expr_stmt|;
name|c
operator|=
operator|(
name|ConstantUtf8
operator|)
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|name
operator|=
name|c
operator|.
name|getBytes
argument_list|()
expr_stmt|;
name|signature
operator|=
name|Utility
operator|.
name|methodSignatureToString
argument_list|(
name|signature
argument_list|,
name|name
argument_list|,
name|access
argument_list|,
literal|true
argument_list|,
name|getLocalVariableTable
argument_list|()
argument_list|)
expr_stmt|;
name|buf
operator|=
operator|new
name|StringBuilder
argument_list|(
name|signature
argument_list|)
expr_stmt|;
for|for
control|(
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
operator|(
name|attribute
operator|instanceof
name|Code
operator|)
operator|||
operator|(
name|attribute
operator|instanceof
name|ExceptionTable
operator|)
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
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"]"
argument_list|)
expr_stmt|;
block|}
block|}
name|ExceptionTable
name|e
init|=
name|getExceptionTable
argument_list|()
decl_stmt|;
if|if
condition|(
name|e
operator|!=
literal|null
condition|)
block|{
name|String
name|str
init|=
name|e
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|str
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\n\t\tthrows "
argument_list|)
operator|.
name|append
argument_list|(
name|str
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
comment|/**      * @return deep copy of this method      */
specifier|public
specifier|final
name|Method
name|copy
parameter_list|(
name|ConstantPool
name|_constant_pool
parameter_list|)
block|{
return|return
operator|(
name|Method
operator|)
name|copy_
argument_list|(
name|_constant_pool
argument_list|)
return|;
block|}
comment|/**      * @return return type of method      */
specifier|public
name|Type
name|getReturnType
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
comment|/**      * @return array of method argument types      */
specifier|public
name|Type
index|[]
name|getArgumentTypes
parameter_list|()
block|{
return|return
name|Type
operator|.
name|getArgumentTypes
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
name|_cmp
return|;
block|}
comment|/**      * @param comparator Comparison strategy object      */
specifier|public
specifier|static
name|void
name|setComparator
parameter_list|(
name|BCELComparator
name|comparator
parameter_list|)
block|{
name|_cmp
operator|=
name|comparator
expr_stmt|;
block|}
comment|/**      * Return value as defined by given BCELComparator strategy.      * By default two method objects are said to be equal when      * their names and signatures are equal.      *       * @see java.lang.Object#equals(java.lang.Object)      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|_cmp
operator|.
name|equals
argument_list|(
name|this
argument_list|,
name|obj
argument_list|)
return|;
block|}
comment|/**      * Return value as defined by given BCELComparator strategy.      * By default return the hashcode of the method's name XOR signature.      *       * @see java.lang.Object#hashCode()      */
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|_cmp
operator|.
name|hashCode
argument_list|(
name|this
argument_list|)
return|;
block|}
comment|/**      * @return Annotations on the parameters of a method      * @since 6.0      */
specifier|public
name|ParameterAnnotationEntry
index|[]
name|getParameterAnnotationEntries
parameter_list|()
block|{
if|if
condition|(
name|parameterAnnotationEntries
operator|==
literal|null
condition|)
block|{
name|parameterAnnotationEntries
operator|=
name|ParameterAnnotationEntry
operator|.
name|createParameterAnnotationEntries
argument_list|(
name|getAttributes
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|parameterAnnotationEntries
return|;
block|}
block|}
end_class

end_unit

