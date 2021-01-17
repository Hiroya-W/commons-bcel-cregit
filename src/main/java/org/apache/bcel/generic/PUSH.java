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
comment|/**  * Wrapper class for push operations, which are implemented either as BIPUSH,  * LDC or xCONST_n instructions.  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|PUSH
implements|implements
name|CompoundInstruction
implements|,
name|VariableLengthInstruction
implements|,
name|InstructionConstants
block|{
specifier|private
specifier|final
name|Instruction
name|instruction
decl_stmt|;
comment|/**      * This constructor also applies for values of type short, char, byte      *      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|int
name|value
parameter_list|)
block|{
if|if
condition|(
operator|(
name|value
operator|>=
operator|-
literal|1
operator|)
operator|&&
operator|(
name|value
operator|<=
literal|5
operator|)
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|getInstruction
argument_list|(
name|Const
operator|.
name|ICONST_0
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|Instruction
operator|.
name|isValidByte
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|instruction
operator|=
operator|new
name|BIPUSH
argument_list|(
operator|(
name|byte
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|Instruction
operator|.
name|isValidShort
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|instruction
operator|=
operator|new
name|SIPUSH
argument_list|(
operator|(
name|short
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|instruction
operator|=
operator|new
name|LDC
argument_list|(
name|cp
operator|.
name|addInteger
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|boolean
name|value
parameter_list|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|getInstruction
argument_list|(
name|Const
operator|.
name|ICONST_0
operator|+
operator|(
name|value
condition|?
literal|1
else|:
literal|0
operator|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|float
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|0.0
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|FCONST_0
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|==
literal|1.0
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|FCONST_1
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|==
literal|2.0
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|FCONST_2
expr_stmt|;
block|}
else|else
block|{
name|instruction
operator|=
operator|new
name|LDC
argument_list|(
name|cp
operator|.
name|addFloat
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|long
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|0
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|LCONST_0
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|==
literal|1
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|LCONST_1
expr_stmt|;
block|}
else|else
block|{
name|instruction
operator|=
operator|new
name|LDC2_W
argument_list|(
name|cp
operator|.
name|addLong
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|double
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|0.0
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|DCONST_0
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|==
literal|1.0
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|DCONST_1
expr_stmt|;
block|}
else|else
block|{
name|instruction
operator|=
operator|new
name|LDC2_W
argument_list|(
name|cp
operator|.
name|addDouble
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|ACONST_NULL
expr_stmt|;
block|}
else|else
block|{
name|instruction
operator|=
operator|new
name|LDC
argument_list|(
name|cp
operator|.
name|addString
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      *      * @param cp      * @param value      * @since 6.0      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|ObjectType
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|instruction
operator|=
name|InstructionConst
operator|.
name|ACONST_NULL
expr_stmt|;
block|}
else|else
block|{
name|instruction
operator|=
operator|new
name|LDC
argument_list|(
name|cp
operator|.
name|addClass
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|Number
name|value
parameter_list|)
block|{
if|if
condition|(
operator|(
name|value
operator|instanceof
name|Integer
operator|)
operator|||
operator|(
name|value
operator|instanceof
name|Short
operator|)
operator|||
operator|(
name|value
operator|instanceof
name|Byte
operator|)
condition|)
block|{
name|instruction
operator|=
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
name|value
operator|.
name|intValue
argument_list|()
argument_list|)
operator|.
name|instruction
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|instanceof
name|Double
condition|)
block|{
name|instruction
operator|=
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
name|value
operator|.
name|doubleValue
argument_list|()
argument_list|)
operator|.
name|instruction
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|instanceof
name|Float
condition|)
block|{
name|instruction
operator|=
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
name|value
operator|.
name|floatValue
argument_list|()
argument_list|)
operator|.
name|instruction
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|instanceof
name|Long
condition|)
block|{
name|instruction
operator|=
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
name|value
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|instruction
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"What's this: "
operator|+
name|value
argument_list|)
throw|;
block|}
block|}
comment|/**      * creates a push object from a Character value. Warning: Make sure not to attempt to allow      * autoboxing to create this value parameter, as an alternative constructor will be called      *      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|Character
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|cp
argument_list|,
name|value
operator|.
name|charValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param cp Constant pool      * @param value to be pushed      */
specifier|public
name|PUSH
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|,
specifier|final
name|Boolean
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|cp
argument_list|,
name|value
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|InstructionList
name|getInstructionList
parameter_list|()
block|{
return|return
operator|new
name|InstructionList
argument_list|(
name|instruction
argument_list|)
return|;
block|}
specifier|public
name|Instruction
name|getInstruction
parameter_list|()
block|{
return|return
name|instruction
return|;
block|}
comment|/**      * @return mnemonic for instruction      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|instruction
operator|+
literal|" (PUSH)"
return|;
block|}
block|}
end_class

end_unit

