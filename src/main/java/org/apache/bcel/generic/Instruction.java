begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2004 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");   *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
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
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
comment|/**   * Abstract super class for all Java byte codes.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Instruction
implements|implements
name|Cloneable
implements|,
name|Serializable
block|{
specifier|protected
name|short
name|length
init|=
literal|1
decl_stmt|;
comment|// Length of instruction in bytes
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
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|Instruction
parameter_list|()
block|{
block|}
specifier|public
name|Instruction
parameter_list|(
name|short
name|opcode
parameter_list|,
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
name|Constants
operator|.
name|OPCODE_NAMES
index|[
name|opcode
index|]
return|;
block|}
comment|/**      * Long output format:      *      *&lt;name of opcode&gt; "["&lt;opcode number&gt;"]"       * "("&lt;length of instruction&gt;")"      *      * @param verbose long/short format switch      * @return mnemonic for instruction      */
specifier|public
name|String
name|toString
parameter_list|(
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
else|else
block|{
return|return
name|getName
argument_list|()
return|;
block|}
block|}
comment|/**      * @return mnemonic for instruction in verbose format      */
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
comment|/**      * Use with caution, since `BranchInstruction's have a `target' reference which      * is not copied correctly (only basic types are). This also applies for       * `Select' instructions with their multiple branch targets.      *      * @see BranchInstruction      * @return (shallow) copy of an instruction      */
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
name|InstructionConstants
operator|.
name|INSTRUCTIONS
index|[
name|this
operator|.
name|getOpcode
argument_list|()
index|]
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
comment|/**      * Read needed data (e.g. index) from file.      *      * @param bytes byte sequence to read from      * @param wide "wide" instruction flag      */
specifier|protected
name|void
name|initFromFile
parameter_list|(
name|ByteSequence
name|bytes
parameter_list|,
name|boolean
name|wide
parameter_list|)
throws|throws
name|IOException
block|{
block|}
comment|/**      * Read an instruction from (byte code) input stream and return the      * appropiate object.      *      * @param bytes input stream bytes      * @return instruction object being read      */
specifier|public
specifier|static
specifier|final
name|Instruction
name|readInstruction
parameter_list|(
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
name|Constants
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
if|if
condition|(
name|InstructionConstants
operator|.
name|INSTRUCTIONS
index|[
name|opcode
index|]
operator|!=
literal|null
condition|)
block|{
return|return
name|InstructionConstants
operator|.
name|INSTRUCTIONS
index|[
name|opcode
index|]
return|;
comment|// Used predefined immutable object, if available
block|}
switch|switch
condition|(
name|opcode
condition|)
block|{
case|case
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|Constants
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
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Constants
operator|.
name|CONSUME_STACK
index|[
name|opcode
index|]
return|;
block|}
comment|/**      * This method also gives right results for instructions whose      * effect on the stack depends on the constant pool entry they      * reference.      * @return Number of words produced onto stack by this instruction,      * or Constants.UNPREDICTABLE, if this can not be computed statically      */
specifier|public
name|int
name|produceStack
parameter_list|(
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Constants
operator|.
name|PRODUCE_STACK
index|[
name|opcode
index|]
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
comment|/**      * Needed in readInstruction.      */
specifier|private
name|void
name|setOpcode
parameter_list|(
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
comment|/** Get Comparator object used in the equals() method to determine      * equality of instructions.      *      * @return currently used comparator for equals()      */
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
comment|/** Set comparator to be used for equals().      */
specifier|public
specifier|static
name|void
name|setComparator
parameter_list|(
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
specifier|public
name|boolean
name|equals
parameter_list|(
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
block|}
end_class

end_unit

