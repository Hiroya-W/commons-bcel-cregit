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
name|generic
package|;
end_package

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
comment|/**  * Denotes array type, such as int[][]  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ArrayType
extends|extends
name|ReferenceType
block|{
specifier|private
specifier|final
name|int
name|dimensions
decl_stmt|;
specifier|private
specifier|final
name|Type
name|basicType
decl_stmt|;
comment|/**      * Convenience constructor for array type, e.g. int[]      *      * @param type array type, e.g. T_INT      * @param dimensions array dimensions      */
specifier|public
name|ArrayType
parameter_list|(
specifier|final
name|byte
name|type
parameter_list|,
specifier|final
name|int
name|dimensions
parameter_list|)
block|{
name|this
argument_list|(
name|BasicType
operator|.
name|getType
argument_list|(
name|type
argument_list|)
argument_list|,
name|dimensions
argument_list|)
expr_stmt|;
block|}
comment|/**      * Convenience constructor for reference array type, e.g. Object[]      *      * @param className complete name of class (java.lang.String, e.g.)      * @param dimensions array dimensions      */
specifier|public
name|ArrayType
parameter_list|(
specifier|final
name|String
name|className
parameter_list|,
specifier|final
name|int
name|dimensions
parameter_list|)
block|{
name|this
argument_list|(
name|ObjectType
operator|.
name|getInstance
argument_list|(
name|className
argument_list|)
argument_list|,
name|dimensions
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructor for array of given type      *      * @param type type of array (may be an array itself)      * @param dimensions array dimensions      */
specifier|public
name|ArrayType
parameter_list|(
specifier|final
name|Type
name|type
parameter_list|,
specifier|final
name|int
name|dimensions
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|T_ARRAY
argument_list|,
literal|"<dummy>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|dimensions
operator|<
literal|1
operator|||
name|dimensions
operator|>
name|Const
operator|.
name|MAX_BYTE
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Invalid number of dimensions: "
operator|+
name|dimensions
argument_list|)
throw|;
block|}
switch|switch
condition|(
name|type
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|Const
operator|.
name|T_ARRAY
case|:
specifier|final
name|ArrayType
name|array
init|=
operator|(
name|ArrayType
operator|)
name|type
decl_stmt|;
name|this
operator|.
name|dimensions
operator|=
name|dimensions
operator|+
name|array
operator|.
name|dimensions
expr_stmt|;
name|basicType
operator|=
name|array
operator|.
name|basicType
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|T_VOID
case|:
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Invalid type: void[]"
argument_list|)
throw|;
default|default:
comment|// Basic type or reference
name|this
operator|.
name|dimensions
operator|=
name|dimensions
expr_stmt|;
name|basicType
operator|=
name|type
expr_stmt|;
break|break;
block|}
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
name|this
operator|.
name|dimensions
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|'['
argument_list|)
expr_stmt|;
block|}
name|buf
operator|.
name|append
argument_list|(
name|basicType
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|super
operator|.
name|setSignature
argument_list|(
name|buf
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return true if both type objects refer to the same array type.      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
specifier|final
name|Object
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|instanceof
name|ArrayType
condition|)
block|{
specifier|final
name|ArrayType
name|array
init|=
operator|(
name|ArrayType
operator|)
name|type
decl_stmt|;
return|return
name|array
operator|.
name|dimensions
operator|==
name|dimensions
operator|&&
name|array
operator|.
name|basicType
operator|.
name|equals
argument_list|(
name|basicType
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * @return basic type of array, i.e., for int[][][] the basic type is int      */
specifier|public
name|Type
name|getBasicType
parameter_list|()
block|{
return|return
name|basicType
return|;
block|}
comment|/**      * Gets the name of referenced class.      *      * @return name of referenced class.      * @since 6.7.0      */
annotation|@
name|Override
specifier|public
name|String
name|getClassName
parameter_list|()
block|{
return|return
name|signature
return|;
block|}
comment|/**      * @return number of dimensions of array      */
specifier|public
name|int
name|getDimensions
parameter_list|()
block|{
return|return
name|dimensions
return|;
block|}
comment|/**      * @return element type of array, i.e., for int[][][] the element type is int[][]      */
specifier|public
name|Type
name|getElementType
parameter_list|()
block|{
if|if
condition|(
name|dimensions
operator|==
literal|1
condition|)
block|{
return|return
name|basicType
return|;
block|}
return|return
operator|new
name|ArrayType
argument_list|(
name|basicType
argument_list|,
name|dimensions
operator|-
literal|1
argument_list|)
return|;
block|}
comment|/**      * @return a hash code value for the object.      */
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|basicType
operator|.
name|hashCode
argument_list|()
operator|^
name|dimensions
return|;
block|}
block|}
end_class

end_unit

