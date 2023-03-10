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
name|ExceptionConst
import|;
end_import

begin_comment
comment|/**  * Super class for instructions dealing with array access such as IALOAD.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ArrayInstruction
extends|extends
name|Instruction
implements|implements
name|ExceptionThrower
implements|,
name|TypedInstruction
block|{
comment|/**      * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.      */
name|ArrayInstruction
parameter_list|()
block|{
block|}
comment|/**      * @param opcode of instruction      */
specifier|protected
name|ArrayInstruction
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
operator|(
name|short
operator|)
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|getExceptions
parameter_list|()
block|{
return|return
name|ExceptionConst
operator|.
name|createExceptions
argument_list|(
name|ExceptionConst
operator|.
name|EXCS
operator|.
name|EXCS_ARRAY_EXCEPTION
argument_list|)
return|;
block|}
comment|/**      * @return type associated with the instruction      */
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
specifier|final
name|short
name|opcode
init|=
name|super
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|opcode
condition|)
block|{
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|IALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|IASTORE
case|:
return|return
name|Type
operator|.
name|INT
return|;
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|CALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|CASTORE
case|:
return|return
name|Type
operator|.
name|CHAR
return|;
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|BALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|BASTORE
case|:
return|return
name|Type
operator|.
name|BYTE
return|;
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|SALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|SASTORE
case|:
return|return
name|Type
operator|.
name|SHORT
return|;
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|LALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|LASTORE
case|:
return|return
name|Type
operator|.
name|LONG
return|;
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|DALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|DASTORE
case|:
return|return
name|Type
operator|.
name|DOUBLE
return|;
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|FALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|FASTORE
case|:
return|return
name|Type
operator|.
name|FLOAT
return|;
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|AALOAD
case|:
case|case
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|AASTORE
case|:
return|return
name|Type
operator|.
name|OBJECT
return|;
default|default:
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Unknown case in switch"
operator|+
name|opcode
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

