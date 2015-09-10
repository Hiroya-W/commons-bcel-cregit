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
name|generic
package|;
end_package

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
name|classfile
operator|.
name|LineNumber
import|;
end_import

begin_comment
comment|/**   * This class represents a line number within a method, i.e., give an instruction  * a line number corresponding to the source code line.  *  * @version $Id$  * @see     LineNumber  * @see     MethodGen  */
end_comment

begin_class
specifier|public
class|class
name|LineNumberGen
implements|implements
name|InstructionTargeter
implements|,
name|Cloneable
block|{
specifier|private
name|InstructionHandle
name|ih
decl_stmt|;
specifier|private
name|int
name|src_line
decl_stmt|;
comment|/**      * Create a line number.      *      * @param ih instruction handle to reference      */
specifier|public
name|LineNumberGen
parameter_list|(
name|InstructionHandle
name|ih
parameter_list|,
name|int
name|src_line
parameter_list|)
block|{
name|setInstruction
argument_list|(
name|ih
argument_list|)
expr_stmt|;
name|setSourceLine
argument_list|(
name|src_line
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return true, if ih is target of this line number      */
annotation|@
name|Override
specifier|public
name|boolean
name|containsTarget
parameter_list|(
name|InstructionHandle
name|ih
parameter_list|)
block|{
return|return
name|this
operator|.
name|ih
operator|==
name|ih
return|;
block|}
comment|/**      * @param old_ih old target      * @param new_ih new target      */
annotation|@
name|Override
specifier|public
name|void
name|updateTarget
parameter_list|(
name|InstructionHandle
name|old_ih
parameter_list|,
name|InstructionHandle
name|new_ih
parameter_list|)
block|{
if|if
condition|(
name|old_ih
operator|!=
name|ih
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Not targeting "
operator|+
name|old_ih
operator|+
literal|", but "
operator|+
name|ih
operator|+
literal|"}"
argument_list|)
throw|;
block|}
name|setInstruction
argument_list|(
name|new_ih
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get LineNumber attribute .      *      * This relies on that the instruction list has already been dumped to byte code or      * or that the `setPositions' methods has been called for the instruction list.      */
specifier|public
name|LineNumber
name|getLineNumber
parameter_list|()
block|{
return|return
operator|new
name|LineNumber
argument_list|(
name|ih
operator|.
name|getPosition
argument_list|()
argument_list|,
name|src_line
argument_list|)
return|;
block|}
specifier|public
name|void
name|setInstruction
parameter_list|(
name|InstructionHandle
name|ih
parameter_list|)
block|{
comment|// TODO could be package-protected?
if|if
condition|(
name|ih
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"InstructionHandle may not be null"
argument_list|)
throw|;
block|}
name|BranchInstruction
operator|.
name|notifyTarget
argument_list|(
name|this
operator|.
name|ih
argument_list|,
name|ih
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|ih
operator|=
name|ih
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|clone
parameter_list|()
block|{
try|try
block|{
return|return
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
throw|throw
operator|new
name|Error
argument_list|(
literal|"Clone Not Supported"
argument_list|)
throw|;
comment|// never happens
block|}
block|}
specifier|public
name|InstructionHandle
name|getInstruction
parameter_list|()
block|{
return|return
name|ih
return|;
block|}
specifier|public
name|void
name|setSourceLine
parameter_list|(
name|int
name|src_line
parameter_list|)
block|{
comment|// TODO could be package-protected?
name|this
operator|.
name|src_line
operator|=
name|src_line
expr_stmt|;
block|}
specifier|public
name|int
name|getSourceLine
parameter_list|()
block|{
return|return
name|src_line
return|;
block|}
block|}
end_class

end_unit

