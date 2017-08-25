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
name|generic
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|AnnotationEntry
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
name|Annotations
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
name|Constant
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
name|ConstantObject
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
name|Field
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
comment|/**  * Template class for building up a field.  The only extraordinary thing  * one can do is to add a constant value attribute to a field (which must of  * course be compatible with to the declared type).  *  * @version $Id$  * @see Field  */
end_comment

begin_class
specifier|public
class|class
name|FieldGen
extends|extends
name|FieldGenOrMethodGen
block|{
specifier|private
name|Object
name|value
init|=
literal|null
decl_stmt|;
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
name|FieldGen
name|THIS
init|=
operator|(
name|FieldGen
operator|)
name|o1
decl_stmt|;
specifier|final
name|FieldGen
name|THAT
init|=
operator|(
name|FieldGen
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
name|FieldGen
name|THIS
init|=
operator|(
name|FieldGen
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
comment|/**      * Declare a field. If it is static (isStatic() == true) and has a      * basic type like int or String it may have an initial value      * associated with it as defined by setInitValue().      *      * @param access_flags access qualifiers      * @param type  field type      * @param name field name      * @param cp constant pool      */
specifier|public
name|FieldGen
parameter_list|(
specifier|final
name|int
name|access_flags
parameter_list|,
specifier|final
name|Type
name|type
parameter_list|,
specifier|final
name|String
name|name
parameter_list|,
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
name|super
argument_list|(
name|access_flags
argument_list|)
expr_stmt|;
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|setConstantPool
argument_list|(
name|cp
argument_list|)
expr_stmt|;
block|}
comment|/**      * Instantiate from existing field.      *      * @param field Field object      * @param cp constant pool (must contain the same entries as the field's constant pool)      */
specifier|public
name|FieldGen
parameter_list|(
specifier|final
name|Field
name|field
parameter_list|,
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
name|this
argument_list|(
name|field
operator|.
name|getAccessFlags
argument_list|()
argument_list|,
name|Type
operator|.
name|getType
argument_list|(
name|field
operator|.
name|getSignature
argument_list|()
argument_list|)
argument_list|,
name|field
operator|.
name|getName
argument_list|()
argument_list|,
name|cp
argument_list|)
expr_stmt|;
specifier|final
name|Attribute
index|[]
name|attrs
init|=
name|field
operator|.
name|getAttributes
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|Attribute
name|attr
range|:
name|attrs
control|)
block|{
if|if
condition|(
name|attr
operator|instanceof
name|ConstantValue
condition|)
block|{
name|setValue
argument_list|(
operator|(
operator|(
name|ConstantValue
operator|)
name|attr
operator|)
operator|.
name|getConstantValueIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|attr
operator|instanceof
name|Annotations
condition|)
block|{
specifier|final
name|Annotations
name|runtimeAnnotations
init|=
operator|(
name|Annotations
operator|)
name|attr
decl_stmt|;
specifier|final
name|AnnotationEntry
index|[]
name|annotationEntries
init|=
name|runtimeAnnotations
operator|.
name|getAnnotationEntries
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|AnnotationEntry
name|element
range|:
name|annotationEntries
control|)
block|{
name|addAnnotationEntry
argument_list|(
operator|new
name|AnnotationEntryGen
argument_list|(
name|element
argument_list|,
name|cp
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|addAttribute
argument_list|(
name|attr
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|setValue
parameter_list|(
specifier|final
name|int
name|index
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|cp
init|=
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|Constant
name|c
init|=
name|cp
operator|.
name|getConstant
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|value
operator|=
operator|(
operator|(
name|ConstantObject
operator|)
name|c
operator|)
operator|.
name|getConstantValue
argument_list|(
name|cp
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set (optional) initial value of field, otherwise it will be set to null/0/false      * by the JVM automatically.      */
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|String
name|str
parameter_list|)
block|{
name|checkType
argument_list|(
name|ObjectType
operator|.
name|getInstance
argument_list|(
literal|"java.lang.String"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|str
operator|!=
literal|null
condition|)
block|{
name|value
operator|=
name|str
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|long
name|l
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|LONG
argument_list|)
expr_stmt|;
if|if
condition|(
name|l
operator|!=
literal|0L
condition|)
block|{
name|value
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|int
name|i
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|INT
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|!=
literal|0
condition|)
block|{
name|value
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|short
name|s
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|SHORT
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|!=
literal|0
condition|)
block|{
name|value
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|char
name|c
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|CHAR
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|!=
literal|0
condition|)
block|{
name|value
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|byte
name|b
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|)
expr_stmt|;
if|if
condition|(
name|b
operator|!=
literal|0
condition|)
block|{
name|value
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|boolean
name|b
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|BOOLEAN
argument_list|)
expr_stmt|;
if|if
condition|(
name|b
condition|)
block|{
name|value
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|float
name|f
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|FLOAT
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|!=
literal|0.0
condition|)
block|{
name|value
operator|=
operator|new
name|Float
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setInitValue
parameter_list|(
specifier|final
name|double
name|d
parameter_list|)
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|DOUBLE
argument_list|)
expr_stmt|;
if|if
condition|(
name|d
operator|!=
literal|0.0
condition|)
block|{
name|value
operator|=
operator|new
name|Double
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Remove any initial value.      */
specifier|public
name|void
name|cancelInitValue
parameter_list|()
block|{
name|value
operator|=
literal|null
expr_stmt|;
block|}
specifier|private
name|void
name|checkType
parameter_list|(
specifier|final
name|Type
name|atype
parameter_list|)
block|{
specifier|final
name|Type
name|superType
init|=
name|super
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|superType
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"You haven't defined the type of the field yet"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|isFinal
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Only final fields may have an initial value!"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|superType
operator|.
name|equals
argument_list|(
name|atype
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Types are not compatible: "
operator|+
name|superType
operator|+
literal|" vs. "
operator|+
name|atype
argument_list|)
throw|;
block|}
block|}
comment|/**      * Get field object after having set up all necessary values.      */
specifier|public
name|Field
name|getField
parameter_list|()
block|{
specifier|final
name|String
name|signature
init|=
name|getSignature
argument_list|()
decl_stmt|;
specifier|final
name|int
name|name_index
init|=
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addUtf8
argument_list|(
name|super
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|int
name|signature_index
init|=
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addUtf8
argument_list|(
name|signature
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|checkType
argument_list|(
name|super
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|int
name|index
init|=
name|addConstant
argument_list|()
decl_stmt|;
name|addAttribute
argument_list|(
operator|new
name|ConstantValue
argument_list|(
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addUtf8
argument_list|(
literal|"ConstantValue"
argument_list|)
argument_list|,
literal|2
argument_list|,
name|index
argument_list|,
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantPool
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// sic
block|}
name|addAnnotationsAsAttribute
argument_list|(
name|super
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|Field
argument_list|(
name|super
operator|.
name|getAccessFlags
argument_list|()
argument_list|,
name|name_index
argument_list|,
name|signature_index
argument_list|,
name|getAttributes
argument_list|()
argument_list|,
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantPool
argument_list|()
argument_list|)
return|;
comment|// sic
block|}
specifier|private
name|void
name|addAnnotationsAsAttribute
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
specifier|final
name|Attribute
index|[]
name|attrs
init|=
name|AnnotationEntryGen
operator|.
name|getAnnotationAttributes
argument_list|(
name|cp
argument_list|,
name|super
operator|.
name|getAnnotationEntries
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|Attribute
name|attr
range|:
name|attrs
control|)
block|{
name|addAttribute
argument_list|(
name|attr
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|int
name|addConstant
parameter_list|()
block|{
switch|switch
condition|(
name|super
operator|.
name|getType
argument_list|()
operator|.
name|getType
argument_list|()
condition|)
block|{
comment|// sic
case|case
name|Const
operator|.
name|T_INT
case|:
case|case
name|Const
operator|.
name|T_CHAR
case|:
case|case
name|Const
operator|.
name|T_BYTE
case|:
case|case
name|Const
operator|.
name|T_BOOLEAN
case|:
case|case
name|Const
operator|.
name|T_SHORT
case|:
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addInteger
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|value
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
return|;
case|case
name|Const
operator|.
name|T_FLOAT
case|:
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addFloat
argument_list|(
operator|(
operator|(
name|Float
operator|)
name|value
operator|)
operator|.
name|floatValue
argument_list|()
argument_list|)
return|;
case|case
name|Const
operator|.
name|T_DOUBLE
case|:
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addDouble
argument_list|(
operator|(
operator|(
name|Double
operator|)
name|value
operator|)
operator|.
name|doubleValue
argument_list|()
argument_list|)
return|;
case|case
name|Const
operator|.
name|T_LONG
case|:
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addLong
argument_list|(
operator|(
operator|(
name|Long
operator|)
name|value
operator|)
operator|.
name|longValue
argument_list|()
argument_list|)
return|;
case|case
name|Const
operator|.
name|T_REFERENCE
case|:
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|addString
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Oops: Unhandled : "
operator|+
name|super
operator|.
name|getType
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
throw|;
comment|// sic
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getSignature
parameter_list|()
block|{
return|return
name|super
operator|.
name|getType
argument_list|()
operator|.
name|getSignature
argument_list|()
return|;
block|}
specifier|private
name|List
argument_list|<
name|FieldObserver
argument_list|>
name|observers
decl_stmt|;
comment|/** Add observer for this object.      */
specifier|public
name|void
name|addObserver
parameter_list|(
specifier|final
name|FieldObserver
name|o
parameter_list|)
block|{
if|if
condition|(
name|observers
operator|==
literal|null
condition|)
block|{
name|observers
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
name|observers
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
comment|/** Remove observer for this object.      */
specifier|public
name|void
name|removeObserver
parameter_list|(
specifier|final
name|FieldObserver
name|o
parameter_list|)
block|{
if|if
condition|(
name|observers
operator|!=
literal|null
condition|)
block|{
name|observers
operator|.
name|remove
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Call notify() method on all observers. This method is not called      * automatically whenever the state has changed, but has to be      * called by the user after he has finished editing the object.      */
specifier|public
name|void
name|update
parameter_list|()
block|{
if|if
condition|(
name|observers
operator|!=
literal|null
condition|)
block|{
for|for
control|(
specifier|final
name|FieldObserver
name|observer
range|:
name|observers
control|)
block|{
name|observer
operator|.
name|notify
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|String
name|getInitValue
parameter_list|()
block|{
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
return|return
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Return string representation close to declaration format,      * `public static final short MAX = 100', e.g..      *      * @return String representation of field      */
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
name|super
operator|.
name|getType
argument_list|()
operator|.
name|toString
argument_list|()
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
literal|32
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
name|String
name|value
init|=
name|getInitValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
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
name|value
argument_list|)
expr_stmt|;
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** @return deep copy of this field      */
specifier|public
name|FieldGen
name|copy
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
specifier|final
name|FieldGen
name|fg
init|=
operator|(
name|FieldGen
operator|)
name|clone
argument_list|()
decl_stmt|;
name|fg
operator|.
name|setConstantPool
argument_list|(
name|cp
argument_list|)
expr_stmt|;
return|return
name|fg
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
comment|/**      * Return value as defined by given BCELComparator strategy.      * By default two FieldGen objects are said to be equal when      * their names and signatures are equal.      *      * @see java.lang.Object#equals(java.lang.Object)      */
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

