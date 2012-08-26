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
name|DataInputStream
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
name|io
operator|.
name|Serializable
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
name|util
operator|.
name|BCELComparator
import|;
end_import

begin_comment
comment|/**  * Abstract superclass for classes to represent the different constant types  * in the constant pool of a class file. The classes keep closely to  * the JVM specification.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Constant
implements|implements
name|Cloneable
implements|,
name|Node
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5739037344085356353L
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
name|Constant
name|THIS
init|=
operator|(
name|Constant
operator|)
name|o1
decl_stmt|;
name|Constant
name|THAT
init|=
operator|(
name|Constant
operator|)
name|o2
decl_stmt|;
return|return
name|THIS
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|THAT
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
name|Constant
name|THIS
init|=
operator|(
name|Constant
operator|)
name|o
decl_stmt|;
return|return
name|THIS
operator|.
name|toString
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
decl_stmt|;
comment|/* In fact this tag is redundant since we can distinguish different      * `Constant' objects by their type, i.e., via `instanceof'. In some      * places we will use the tag for switch()es anyway.      *      * First, we want match the specification as closely as possible. Second we      * need the tag as an index to select the corresponding class name from the       * `CONSTANT_NAMES' array.      */
specifier|protected
name|byte
name|tag
decl_stmt|;
name|Constant
parameter_list|(
name|byte
name|tag
parameter_list|)
block|{
name|this
operator|.
name|tag
operator|=
name|tag
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
specifier|public
specifier|abstract
name|void
name|accept
parameter_list|(
name|Visitor
name|v
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * @return Tag of constant, i.e., its type. No setTag() method to avoid      * confusion.      */
specifier|public
specifier|final
name|byte
name|getTag
parameter_list|()
block|{
return|return
name|tag
return|;
block|}
comment|/**      * @return String representation.      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|Constants
operator|.
name|CONSTANT_NAMES
index|[
name|tag
index|]
operator|+
literal|"["
operator|+
name|tag
operator|+
literal|"]"
return|;
block|}
comment|/**      * @return deep copy of this constant      */
specifier|public
name|Constant
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|Constant
operator|)
name|super
operator|.
name|clone
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|clone
parameter_list|()
throws|throws
name|CloneNotSupportedException
block|{
return|return
name|super
operator|.
name|clone
argument_list|()
return|;
block|}
comment|/**      * Read one constant from the given file, the type depends on a tag byte.      *      * @param file Input stream      * @return Constant object      */
specifier|static
specifier|final
name|Constant
name|readConstant
parameter_list|(
name|DataInputStream
name|file
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|byte
name|b
init|=
name|file
operator|.
name|readByte
argument_list|()
decl_stmt|;
comment|// Read tag byte
switch|switch
condition|(
name|b
condition|)
block|{
case|case
name|Constants
operator|.
name|CONSTANT_Class
case|:
return|return
operator|new
name|ConstantClass
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_Fieldref
case|:
return|return
operator|new
name|ConstantFieldref
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_Methodref
case|:
return|return
operator|new
name|ConstantMethodref
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_InterfaceMethodref
case|:
return|return
operator|new
name|ConstantInterfaceMethodref
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_String
case|:
return|return
operator|new
name|ConstantString
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_Integer
case|:
return|return
operator|new
name|ConstantInteger
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_Float
case|:
return|return
operator|new
name|ConstantFloat
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_Long
case|:
return|return
operator|new
name|ConstantLong
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_Double
case|:
return|return
operator|new
name|ConstantDouble
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_NameAndType
case|:
return|return
operator|new
name|ConstantNameAndType
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_Utf8
case|:
return|return
operator|new
name|ConstantUtf8
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_MethodHandle
case|:
return|return
operator|new
name|ConstantMethodHandle
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_MethodType
case|:
return|return
operator|new
name|ConstantMethodType
argument_list|(
name|file
argument_list|)
return|;
case|case
name|Constants
operator|.
name|CONSTANT_InvokeDynamic
case|:
return|return
operator|new
name|ConstantInvokeDynamic
argument_list|(
name|file
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Invalid byte tag in constant pool: "
operator|+
name|b
argument_list|)
throw|;
block|}
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
comment|/**      * Return value as defined by given BCELComparator strategy.      * By default two Constant objects are said to be equal when      * the result of toString() is equal.      *       * @see java.lang.Object#equals(java.lang.Object)      */
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
comment|/**      * Return value as defined by given BCELComparator strategy.      * By default return the hashcode of the result of toString().      *       * @see java.lang.Object#hashCode()      */
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
block|}
end_class

end_unit

