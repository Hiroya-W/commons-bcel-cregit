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
name|ConstantClass
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
comment|/**  * Abstract super class for instructions that use an index into the  * constant pool such as LDC, INVOKEVIRTUAL, etc.  *  * @see ConstantPoolGen  * @see LDC  * @see INVOKEVIRTUAL  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|CPInstruction
extends|extends
name|Instruction
implements|implements
name|TypedInstruction
implements|,
name|IndexedInstruction
block|{
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|Deprecated
specifier|protected
name|int
name|index
decl_stmt|;
comment|// index to constant pool
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|CPInstruction
parameter_list|()
block|{
block|}
comment|/**      * @param index to constant pool      */
specifier|protected
name|CPInstruction
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|,
specifier|final
name|int
name|index
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
operator|(
name|short
operator|)
literal|3
argument_list|)
expr_stmt|;
name|setIndex
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      * @param out Output stream      */
annotation|@
name|Override
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
name|super
operator|.
name|getOpcode
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeShort
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * Long output format:      *      *&lt;name of opcode&gt; "["&lt;opcode number&gt;"]"      * "("&lt;length of instruction&gt;")" "&lt;"&lt; constant pool index&gt;"&gt;"      *      * @param verbose long/short format switch      * @return mnemonic for instruction      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|boolean
name|verbose
parameter_list|)
block|{
return|return
name|super
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
operator|+
literal|" "
operator|+
name|index
return|;
block|}
comment|/**      * @return mnemonic for instruction with symbolic references resolved      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|ConstantPool
name|cp
parameter_list|)
block|{
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
name|String
name|str
init|=
name|cp
operator|.
name|constantToString
argument_list|(
name|c
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|instanceof
name|ConstantClass
condition|)
block|{
name|str
operator|=
name|str
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
expr_stmt|;
block|}
return|return
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|getOpcodeName
argument_list|(
name|super
operator|.
name|getOpcode
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|str
return|;
block|}
comment|/**      * Read needed data (i.e., index) from file.      * @param bytes input stream      * @param wide wide prefix?      */
annotation|@
name|Override
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
name|setIndex
argument_list|(
name|bytes
operator|.
name|readUnsignedShort
argument_list|()
argument_list|)
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|3
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return index in constant pool referred by this instruction.      */
annotation|@
name|Override
specifier|public
specifier|final
name|int
name|getIndex
parameter_list|()
block|{
return|return
name|index
return|;
block|}
comment|/**      * Set the index to constant pool.      * @param index in  constant pool.      */
annotation|@
name|Override
specifier|public
name|void
name|setIndex
parameter_list|(
specifier|final
name|int
name|index
parameter_list|)
block|{
comment|// TODO could be package-protected?
if|if
condition|(
name|index
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Negative index value: "
operator|+
name|index
argument_list|)
throw|;
block|}
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
block|}
comment|/** @return type related with this instruction.      */
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|cp
operator|.
name|getConstantString
argument_list|(
name|index
argument_list|,
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|name
operator|.
name|startsWith
argument_list|(
literal|"["
argument_list|)
condition|)
block|{
name|name
operator|=
literal|"L"
operator|+
name|name
operator|+
literal|";"
expr_stmt|;
block|}
return|return
name|Type
operator|.
name|getType
argument_list|(
name|name
argument_list|)
return|;
block|}
block|}
end_class

end_unit

