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
name|util
operator|.
name|ByteSequence
import|;
end_import

begin_comment
comment|/**  * Abstract super class for all Java byte codes.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Instruction
implements|implements
name|Cloneable
block|{
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|Deprecated
specifier|protected
name|short
name|length
init|=
literal|1
decl_stmt|;
comment|// Length of instruction in bytes
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|Deprecated
specifier|protected
name|short
name|opcode
init|=
operator|-
literal|1
decl_stmt|;
comment|// Opcode number
specifier|private
specifier|static
name|InstructionComparator
name|cmp
init|=
name|InstructionComparator
operator|.
name|DEFAULT
decl_stmt|;
comment|/**      * Empty constructor needed for Instruction.readInstruction.      * Not to be used otherwise.      */
name|Instruction
parameter_list|()
block|{
block|}
specifier|public
name|Instruction
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|,
specifier|final
name|short
name|length
parameter_list|)
block|{
name|this
operator|.
name|length
operator|=
name|length
expr_stmt|;
name|this
operator|.
name|opcode
operator|=
name|opcode
expr_stmt|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      * @param out Output stream      */
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|opcode
argument_list|)
expr_stmt|;
comment|// Common for all instructions
block|}
comment|/** @return name of instruction, i.e., opcode name      */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Const
operator|.
name|getOpcodeName
argument_list|(
name|opcode
argument_list|)
return|;
block|}
comment|/**      * Long output format:      *      *&lt;name of opcode&gt; "["&lt;opcode number&gt;"]"      * "("&lt;length of instruction&gt;")"      *      * @param verbose long/short format switch      * @return mnemonic for instruction      */
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|boolean
name|verbose
parameter_list|)
block|{
if|if
condition|(
name|verbose
condition|)
block|{
return|return
name|getName
argument_list|()
operator|+
literal|"["
operator|+
name|opcode
operator|+
literal|"]("
operator|+
name|length
operator|+
literal|")"
return|;
block|}
return|return
name|getName
argument_list|()
return|;
block|}
comment|/**      * @return mnemonic for instruction in verbose format      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toString
argument_list|(
literal|true
argument_list|)
return|;
block|}
comment|/**      * @return mnemonic for instruction with sumbolic references resolved      */
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|ConstantPool
name|cp
parameter_list|)
block|{
return|return
name|toString
argument_list|(
literal|false
argument_list|)
return|;
block|}
comment|/**      * Use with caution, since `BranchInstruction's have a `target' reference which      * is not copied correctly (only basic types are). This also applies for      * `Select' instructions with their multiple branch targets.      *      * @see BranchInstruction      * @return (shallow) copy of an instruction      */
specifier|public
name|Instruction
name|copy
parameter_list|()
block|{
name|Instruction
name|i
init|=
literal|null
decl_stmt|;
comment|// "Constant" instruction, no need to duplicate
if|if
condition|(
name|InstructionConst
operator|.
name|getInstruction
argument_list|(
name|this
operator|.
name|getOpcode
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|i
operator|=
name|this
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|i
operator|=
operator|(
name|Instruction
operator|)
name|clone
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|i
return|;
block|}
comment|/**      * Read needed data (e.g. index) from file.      *      * @param bytes byte sequence to read from      * @param wide "wide" instruction flag      * @throws IOException may be thrown if the implementation needs to read data from the file      */
specifier|protected
name|void
name|initFromFile
parameter_list|(
specifier|final
name|ByteSequence
name|bytes
parameter_list|,
specifier|final
name|boolean
name|wide
parameter_list|)
throws|throws
name|IOException
block|{
block|}
comment|/**      * Read an instruction from (byte code) input stream and return the      * appropiate object.      *<p>      * If the Instruction is defined in {@link InstructionConst}, then the      * singleton instance is returned.      * @param bytes input stream bytes      * @return instruction object being read      * @see InstructionConst#getInstruction(int)      */
comment|// @since 6.0 no longer final
specifier|public
specifier|static
name|Instruction
name|readInstruction
parameter_list|(
specifier|final
name|ByteSequence
name|bytes
parameter_list|)
throws|throws
name|IOException
block|{
name|boolean
name|wide
init|=
literal|false
decl_stmt|;
name|short
name|opcode
init|=
operator|(
name|short
operator|)
name|bytes
operator|.
name|readUnsignedByte
argument_list|()
decl_stmt|;
name|Instruction
name|obj
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|opcode
operator|==
name|Const
operator|.
name|WIDE
condition|)
block|{
comment|// Read next opcode after wide byte
name|wide
operator|=
literal|true
expr_stmt|;
name|opcode
operator|=
operator|(
name|short
operator|)
name|bytes
operator|.
name|readUnsignedByte
argument_list|()
expr_stmt|;
block|}
specifier|final
name|Instruction
name|instruction
init|=
name|InstructionConst
operator|.
name|getInstruction
argument_list|(
name|opcode
argument_list|)
decl_stmt|;
if|if
condition|(
name|instruction
operator|!=
literal|null
condition|)
block|{
return|return
name|instruction
return|;
comment|// Used predefined immutable object, if available
block|}
switch|switch
condition|(
name|opcode
condition|)
block|{
case|case
name|Const
operator|.
name|BIPUSH
case|:
name|obj
operator|=
operator|new
name|BIPUSH
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|SIPUSH
case|:
name|obj
operator|=
operator|new
name|SIPUSH
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LDC
case|:
name|obj
operator|=
operator|new
name|LDC
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LDC_W
case|:
name|obj
operator|=
operator|new
name|LDC_W
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LDC2_W
case|:
name|obj
operator|=
operator|new
name|LDC2_W
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ILOAD
case|:
name|obj
operator|=
operator|new
name|ILOAD
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LLOAD
case|:
name|obj
operator|=
operator|new
name|LLOAD
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FLOAD
case|:
name|obj
operator|=
operator|new
name|FLOAD
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DLOAD
case|:
name|obj
operator|=
operator|new
name|DLOAD
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ALOAD
case|:
name|obj
operator|=
operator|new
name|ALOAD
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ILOAD_0
case|:
name|obj
operator|=
operator|new
name|ILOAD
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ILOAD_1
case|:
name|obj
operator|=
operator|new
name|ILOAD
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ILOAD_2
case|:
name|obj
operator|=
operator|new
name|ILOAD
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ILOAD_3
case|:
name|obj
operator|=
operator|new
name|ILOAD
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LLOAD_0
case|:
name|obj
operator|=
operator|new
name|LLOAD
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LLOAD_1
case|:
name|obj
operator|=
operator|new
name|LLOAD
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LLOAD_2
case|:
name|obj
operator|=
operator|new
name|LLOAD
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LLOAD_3
case|:
name|obj
operator|=
operator|new
name|LLOAD
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FLOAD_0
case|:
name|obj
operator|=
operator|new
name|FLOAD
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FLOAD_1
case|:
name|obj
operator|=
operator|new
name|FLOAD
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FLOAD_2
case|:
name|obj
operator|=
operator|new
name|FLOAD
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FLOAD_3
case|:
name|obj
operator|=
operator|new
name|FLOAD
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DLOAD_0
case|:
name|obj
operator|=
operator|new
name|DLOAD
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DLOAD_1
case|:
name|obj
operator|=
operator|new
name|DLOAD
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DLOAD_2
case|:
name|obj
operator|=
operator|new
name|DLOAD
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DLOAD_3
case|:
name|obj
operator|=
operator|new
name|DLOAD
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ALOAD_0
case|:
name|obj
operator|=
operator|new
name|ALOAD
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ALOAD_1
case|:
name|obj
operator|=
operator|new
name|ALOAD
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ALOAD_2
case|:
name|obj
operator|=
operator|new
name|ALOAD
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ALOAD_3
case|:
name|obj
operator|=
operator|new
name|ALOAD
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ISTORE
case|:
name|obj
operator|=
operator|new
name|ISTORE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LSTORE
case|:
name|obj
operator|=
operator|new
name|LSTORE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FSTORE
case|:
name|obj
operator|=
operator|new
name|FSTORE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DSTORE
case|:
name|obj
operator|=
operator|new
name|DSTORE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ASTORE
case|:
name|obj
operator|=
operator|new
name|ASTORE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ISTORE_0
case|:
name|obj
operator|=
operator|new
name|ISTORE
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ISTORE_1
case|:
name|obj
operator|=
operator|new
name|ISTORE
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ISTORE_2
case|:
name|obj
operator|=
operator|new
name|ISTORE
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ISTORE_3
case|:
name|obj
operator|=
operator|new
name|ISTORE
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LSTORE_0
case|:
name|obj
operator|=
operator|new
name|LSTORE
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LSTORE_1
case|:
name|obj
operator|=
operator|new
name|LSTORE
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LSTORE_2
case|:
name|obj
operator|=
operator|new
name|LSTORE
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LSTORE_3
case|:
name|obj
operator|=
operator|new
name|LSTORE
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FSTORE_0
case|:
name|obj
operator|=
operator|new
name|FSTORE
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FSTORE_1
case|:
name|obj
operator|=
operator|new
name|FSTORE
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FSTORE_2
case|:
name|obj
operator|=
operator|new
name|FSTORE
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|FSTORE_3
case|:
name|obj
operator|=
operator|new
name|FSTORE
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DSTORE_0
case|:
name|obj
operator|=
operator|new
name|DSTORE
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DSTORE_1
case|:
name|obj
operator|=
operator|new
name|DSTORE
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DSTORE_2
case|:
name|obj
operator|=
operator|new
name|DSTORE
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|DSTORE_3
case|:
name|obj
operator|=
operator|new
name|DSTORE
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ASTORE_0
case|:
name|obj
operator|=
operator|new
name|ASTORE
argument_list|(
literal|0
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ASTORE_1
case|:
name|obj
operator|=
operator|new
name|ASTORE
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ASTORE_2
case|:
name|obj
operator|=
operator|new
name|ASTORE
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ASTORE_3
case|:
name|obj
operator|=
operator|new
name|ASTORE
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IINC
case|:
name|obj
operator|=
operator|new
name|IINC
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFEQ
case|:
name|obj
operator|=
operator|new
name|IFEQ
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFNE
case|:
name|obj
operator|=
operator|new
name|IFNE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFLT
case|:
name|obj
operator|=
operator|new
name|IFLT
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFGE
case|:
name|obj
operator|=
operator|new
name|IFGE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFGT
case|:
name|obj
operator|=
operator|new
name|IFGT
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFLE
case|:
name|obj
operator|=
operator|new
name|IFLE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ICMPEQ
case|:
name|obj
operator|=
operator|new
name|IF_ICMPEQ
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ICMPNE
case|:
name|obj
operator|=
operator|new
name|IF_ICMPNE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ICMPLT
case|:
name|obj
operator|=
operator|new
name|IF_ICMPLT
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ICMPGE
case|:
name|obj
operator|=
operator|new
name|IF_ICMPGE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ICMPGT
case|:
name|obj
operator|=
operator|new
name|IF_ICMPGT
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ICMPLE
case|:
name|obj
operator|=
operator|new
name|IF_ICMPLE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ACMPEQ
case|:
name|obj
operator|=
operator|new
name|IF_ACMPEQ
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IF_ACMPNE
case|:
name|obj
operator|=
operator|new
name|IF_ACMPNE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|GOTO
case|:
name|obj
operator|=
operator|new
name|GOTO
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|JSR
case|:
name|obj
operator|=
operator|new
name|JSR
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|RET
case|:
name|obj
operator|=
operator|new
name|RET
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|TABLESWITCH
case|:
name|obj
operator|=
operator|new
name|TABLESWITCH
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|LOOKUPSWITCH
case|:
name|obj
operator|=
operator|new
name|LOOKUPSWITCH
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|GETSTATIC
case|:
name|obj
operator|=
operator|new
name|GETSTATIC
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|PUTSTATIC
case|:
name|obj
operator|=
operator|new
name|PUTSTATIC
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|GETFIELD
case|:
name|obj
operator|=
operator|new
name|GETFIELD
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|PUTFIELD
case|:
name|obj
operator|=
operator|new
name|PUTFIELD
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|INVOKEVIRTUAL
case|:
name|obj
operator|=
operator|new
name|INVOKEVIRTUAL
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|INVOKESPECIAL
case|:
name|obj
operator|=
operator|new
name|INVOKESPECIAL
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|INVOKESTATIC
case|:
name|obj
operator|=
operator|new
name|INVOKESTATIC
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|INVOKEINTERFACE
case|:
name|obj
operator|=
operator|new
name|INVOKEINTERFACE
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|INVOKEDYNAMIC
case|:
name|obj
operator|=
operator|new
name|INVOKEDYNAMIC
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|NEW
case|:
name|obj
operator|=
operator|new
name|NEW
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|NEWARRAY
case|:
name|obj
operator|=
operator|new
name|NEWARRAY
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|ANEWARRAY
case|:
name|obj
operator|=
operator|new
name|ANEWARRAY
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CHECKCAST
case|:
name|obj
operator|=
operator|new
name|CHECKCAST
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|INSTANCEOF
case|:
name|obj
operator|=
operator|new
name|INSTANCEOF
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|MULTIANEWARRAY
case|:
name|obj
operator|=
operator|new
name|MULTIANEWARRAY
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFNULL
case|:
name|obj
operator|=
operator|new
name|IFNULL
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IFNONNULL
case|:
name|obj
operator|=
operator|new
name|IFNONNULL
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|GOTO_W
case|:
name|obj
operator|=
operator|new
name|GOTO_W
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|JSR_W
case|:
name|obj
operator|=
operator|new
name|JSR_W
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|BREAKPOINT
case|:
name|obj
operator|=
operator|new
name|BREAKPOINT
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IMPDEP1
case|:
name|obj
operator|=
operator|new
name|IMPDEP1
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|IMPDEP2
case|:
name|obj
operator|=
operator|new
name|IMPDEP2
argument_list|()
expr_stmt|;
break|break;
default|default:
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Illegal opcode detected: "
operator|+
name|opcode
argument_list|)
throw|;
block|}
if|if
condition|(
name|wide
operator|&&
operator|!
operator|(
operator|(
name|obj
operator|instanceof
name|LocalVariableInstruction
operator|)
operator|||
operator|(
name|obj
operator|instanceof
name|IINC
operator|)
operator|||
operator|(
name|obj
operator|instanceof
name|RET
operator|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Illegal opcode after wide: "
operator|+
name|opcode
argument_list|)
throw|;
block|}
name|obj
operator|.
name|setOpcode
argument_list|(
name|opcode
argument_list|)
expr_stmt|;
name|obj
operator|.
name|initFromFile
argument_list|(
name|bytes
argument_list|,
name|wide
argument_list|)
expr_stmt|;
comment|// Do further initializations, if any
return|return
name|obj
return|;
block|}
comment|/**      * This method also gives right results for instructions whose      * effect on the stack depends on the constant pool entry they      * reference.      *  @return Number of words consumed from stack by this instruction,      * or Constants.UNPREDICTABLE, if this can not be computed statically      */
specifier|public
name|int
name|consumeStack
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Const
operator|.
name|getConsumeStack
argument_list|(
name|opcode
argument_list|)
return|;
block|}
comment|/**      * This method also gives right results for instructions whose      * effect on the stack depends on the constant pool entry they      * reference.      * @return Number of words produced onto stack by this instruction,      * or Constants.UNPREDICTABLE, if this can not be computed statically      */
specifier|public
name|int
name|produceStack
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Const
operator|.
name|getProduceStack
argument_list|(
name|opcode
argument_list|)
return|;
block|}
comment|/**      * @return this instructions opcode      */
specifier|public
name|short
name|getOpcode
parameter_list|()
block|{
return|return
name|opcode
return|;
block|}
comment|/**      * @return length (in bytes) of instruction      */
specifier|public
name|int
name|getLength
parameter_list|()
block|{
return|return
name|length
return|;
block|}
comment|/**      * Needed in readInstruction and subclasses in this package      */
specifier|final
name|void
name|setOpcode
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|)
block|{
name|this
operator|.
name|opcode
operator|=
name|opcode
expr_stmt|;
block|}
comment|/**      * Needed in readInstruction and subclasses in this package      * @since 6.0      */
specifier|final
name|void
name|setLength
parameter_list|(
specifier|final
name|int
name|length
parameter_list|)
block|{
name|this
operator|.
name|length
operator|=
operator|(
name|short
operator|)
name|length
expr_stmt|;
comment|// TODO check range?
block|}
comment|/** Some instructions may be reused, so don't do anything by default.      */
name|void
name|dispose
parameter_list|()
block|{
block|}
comment|/**      * Call corresponding visitor method(s). The order is:      * Call visitor methods of implemented interfaces first, then      * call methods according to the class hierarchy in descending order,      * i.e., the most specific visitXXX() call comes last.      *      * @param v Visitor object      */
specifier|public
specifier|abstract
name|void
name|accept
parameter_list|(
name|Visitor
name|v
parameter_list|)
function_decl|;
comment|/** Get Comparator object used in the equals() method to determine      * equality of instructions.      *      * @return currently used comparator for equals()      * @deprecated (6.0) use the built in comparator, or wrap this class in another object that implements these methods      */
annotation|@
name|Deprecated
specifier|public
specifier|static
name|InstructionComparator
name|getComparator
parameter_list|()
block|{
return|return
name|cmp
return|;
block|}
comment|/** Set comparator to be used for equals().       * @deprecated (6.0) use the built in comparator, or wrap this class in another object that implements these methods      */
annotation|@
name|Deprecated
specifier|public
specifier|static
name|void
name|setComparator
parameter_list|(
specifier|final
name|InstructionComparator
name|c
parameter_list|)
block|{
name|cmp
operator|=
name|c
expr_stmt|;
block|}
comment|/** Check for equality, delegated to comparator      * @return true if that is an Instruction and has the same opcode      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
specifier|final
name|Object
name|that
parameter_list|)
block|{
return|return
operator|(
name|that
operator|instanceof
name|Instruction
operator|)
condition|?
name|cmp
operator|.
name|equals
argument_list|(
name|this
argument_list|,
operator|(
name|Instruction
operator|)
name|that
argument_list|)
else|:
literal|false
return|;
block|}
comment|/** calculate the hashCode of this object      * @return the hashCode      * @since 6.0      */
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|opcode
return|;
block|}
comment|/**      * Check if the value can fit in a byte (signed)      * @param value the value to check      * @return true if the value is in range      * @since 6.0      */
specifier|public
specifier|static
name|boolean
name|isValidByte
parameter_list|(
specifier|final
name|int
name|value
parameter_list|)
block|{
return|return
name|value
operator|>=
name|Byte
operator|.
name|MIN_VALUE
operator|&&
name|value
operator|<=
name|Byte
operator|.
name|MAX_VALUE
return|;
block|}
comment|/**      * Check if the value can fit in a short (signed)      * @param value the value to check      * @return true if the value is in range      * @since 6.0      */
specifier|public
specifier|static
name|boolean
name|isValidShort
parameter_list|(
specifier|final
name|int
name|value
parameter_list|)
block|{
return|return
name|value
operator|>=
name|Short
operator|.
name|MIN_VALUE
operator|&&
name|value
operator|<=
name|Short
operator|.
name|MAX_VALUE
return|;
block|}
block|}
end_class

end_unit

