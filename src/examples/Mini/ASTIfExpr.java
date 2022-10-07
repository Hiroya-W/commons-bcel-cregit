begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTIfExpr.java */
end_comment

begin_comment
comment|/* JJT: 0.3pre1 */
end_comment

begin_package
package|package
name|Mini
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
name|generic
operator|.
name|BranchHandle
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
name|generic
operator|.
name|ConstantPoolGen
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
name|generic
operator|.
name|GOTO
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
name|generic
operator|.
name|IFEQ
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
name|generic
operator|.
name|InstructionConst
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
name|generic
operator|.
name|InstructionList
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
name|generic
operator|.
name|MethodGen
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
specifier|public
class|class
name|ASTIfExpr
extends|extends
name|ASTExpr
block|{
specifier|public
specifier|static
name|Node
name|jjtCreate
parameter_list|(
specifier|final
name|MiniParser
name|p
parameter_list|,
specifier|final
name|int
name|id
parameter_list|)
block|{
return|return
operator|new
name|ASTIfExpr
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
specifier|private
name|ASTExpr
name|if_expr
decl_stmt|,
name|then_expr
decl_stmt|,
name|else_expr
decl_stmt|;
comment|// Generated methods
name|ASTIfExpr
parameter_list|(
specifier|final
name|int
name|id
parameter_list|)
block|{
name|super
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
name|ASTIfExpr
parameter_list|(
specifier|final
name|MiniParser
name|p
parameter_list|,
specifier|final
name|int
name|id
parameter_list|)
block|{
name|super
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
comment|/**      * Fifth pass, produce Java byte code.      */
annotation|@
name|Override
specifier|public
name|void
name|byte_code
parameter_list|(
specifier|final
name|InstructionList
name|il
parameter_list|,
specifier|final
name|MethodGen
name|method
parameter_list|,
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
name|if_expr
operator|.
name|byte_code
argument_list|(
name|il
argument_list|,
name|method
argument_list|,
name|cp
argument_list|)
expr_stmt|;
specifier|final
name|InstructionList
name|then_code
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
specifier|final
name|InstructionList
name|else_code
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
name|then_expr
operator|.
name|byte_code
argument_list|(
name|then_code
argument_list|,
name|method
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|else_expr
operator|.
name|byte_code
argument_list|(
name|else_code
argument_list|,
name|method
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|BranchHandle
name|i
decl_stmt|,
name|g
decl_stmt|;
name|i
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|IFEQ
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// If POP() == FALSE(i.e. 0) then branch to ELSE
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|then_code
argument_list|)
expr_stmt|;
name|g
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|GOTO
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|i
operator|.
name|setTarget
argument_list|(
name|il
operator|.
name|append
argument_list|(
name|else_code
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|setTarget
argument_list|(
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|NOP
argument_list|)
argument_list|)
expr_stmt|;
comment|// May be optimized away later
block|}
comment|/**      * Overrides ASTExpr.closeNode() Cast children nodes Node[] to appropiate type ASTExpr[]      */
annotation|@
name|Override
specifier|public
name|void
name|closeNode
parameter_list|()
block|{
name|if_expr
operator|=
operator|(
name|ASTExpr
operator|)
name|children
index|[
literal|0
index|]
expr_stmt|;
name|then_expr
operator|=
operator|(
name|ASTExpr
operator|)
name|children
index|[
literal|1
index|]
expr_stmt|;
if|if
condition|(
name|children
operator|.
name|length
operator|==
literal|3
condition|)
block|{
name|else_expr
operator|=
operator|(
name|ASTExpr
operator|)
name|children
index|[
literal|2
index|]
expr_stmt|;
block|}
else|else
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|if_expr
operator|.
name|getLine
argument_list|()
argument_list|,
name|if_expr
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"IF expression has no ELSE branch"
argument_list|)
expr_stmt|;
block|}
name|children
operator|=
literal|null
expr_stmt|;
comment|// Throw away
block|}
comment|/**      * Fourth pass, produce Java code.      */
annotation|@
name|Override
specifier|public
name|void
name|code
parameter_list|(
specifier|final
name|StringBuffer
name|buf
parameter_list|)
block|{
name|if_expr
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"    if("
operator|+
name|ASTFunDecl
operator|.
name|pop
argument_list|()
operator|+
literal|" == 1) {\n"
argument_list|)
expr_stmt|;
specifier|final
name|int
name|size
init|=
name|ASTFunDecl
operator|.
name|size
decl_stmt|;
name|then_expr
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|size
operator|=
name|size
expr_stmt|;
comment|// reset stack
name|buf
operator|.
name|append
argument_list|(
literal|"    } else {\n"
argument_list|)
expr_stmt|;
name|else_expr
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"    }\n"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|String
name|prefix
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|toString
argument_list|(
name|prefix
argument_list|)
argument_list|)
expr_stmt|;
name|if_expr
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
name|then_expr
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
if|if
condition|(
name|else_expr
operator|!=
literal|null
condition|)
block|{
name|else_expr
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Second pass Overrides AstExpr.eval()      *      * @return type of expression      * @param expected type      */
annotation|@
name|Override
specifier|public
name|int
name|eval
parameter_list|(
specifier|final
name|int
name|expected
parameter_list|)
block|{
name|int
name|thenType
decl_stmt|,
name|elseType
decl_stmt|,
name|ifType
decl_stmt|;
if|if
condition|(
operator|(
name|ifType
operator|=
name|if_expr
operator|.
name|eval
argument_list|(
name|Const
operator|.
name|T_BOOLEAN
argument_list|)
operator|)
operator|!=
name|Const
operator|.
name|T_BOOLEAN
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|if_expr
operator|.
name|getLine
argument_list|()
argument_list|,
name|if_expr
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"IF expression is not of type boolean, but "
operator|+
name|Constants
operator|.
name|TYPE_NAMES
index|[
name|ifType
index|]
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
name|thenType
operator|=
name|then_expr
operator|.
name|eval
argument_list|(
name|expected
argument_list|)
expr_stmt|;
if|if
condition|(
name|expected
operator|!=
name|Const
operator|.
name|T_UNKNOWN
operator|&&
name|thenType
operator|!=
name|expected
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|then_expr
operator|.
name|getLine
argument_list|()
argument_list|,
name|then_expr
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"THEN expression is not of expected type "
operator|+
name|Constants
operator|.
name|TYPE_NAMES
index|[
name|expected
index|]
operator|+
literal|" but "
operator|+
name|Constants
operator|.
name|TYPE_NAMES
index|[
name|thenType
index|]
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|else_expr
operator|!=
literal|null
condition|)
block|{
name|elseType
operator|=
name|else_expr
operator|.
name|eval
argument_list|(
name|expected
argument_list|)
expr_stmt|;
if|if
condition|(
name|expected
operator|!=
name|Const
operator|.
name|T_UNKNOWN
operator|&&
name|elseType
operator|!=
name|expected
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|else_expr
operator|.
name|getLine
argument_list|()
argument_list|,
name|else_expr
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"ELSE expression is not of expected type "
operator|+
name|Constants
operator|.
name|TYPE_NAMES
index|[
name|expected
index|]
operator|+
literal|" but "
operator|+
name|Constants
operator|.
name|TYPE_NAMES
index|[
name|elseType
index|]
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|thenType
operator|==
name|Const
operator|.
name|T_UNKNOWN
condition|)
block|{
name|thenType
operator|=
name|elseType
expr_stmt|;
name|then_expr
operator|.
name|setType
argument_list|(
name|elseType
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|elseType
operator|=
name|thenType
expr_stmt|;
name|else_expr
operator|=
name|then_expr
expr_stmt|;
block|}
if|if
condition|(
name|thenType
operator|!=
name|elseType
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|line
argument_list|,
name|column
argument_list|,
literal|"Type mismatch in THEN-ELSE: "
operator|+
name|Constants
operator|.
name|TYPE_NAMES
index|[
name|thenType
index|]
operator|+
literal|" vs. "
operator|+
name|Constants
operator|.
name|TYPE_NAMES
index|[
name|elseType
index|]
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
name|type
operator|=
name|thenType
expr_stmt|;
name|is_simple
operator|=
name|if_expr
operator|.
name|isSimple
argument_list|()
operator|&&
name|then_expr
operator|.
name|isSimple
argument_list|()
operator|&&
name|else_expr
operator|.
name|isSimple
argument_list|()
expr_stmt|;
return|return
name|type
return|;
block|}
comment|/**      * Overrides ASTExpr.traverse()      */
annotation|@
name|Override
specifier|public
name|ASTExpr
name|traverse
parameter_list|(
specifier|final
name|Environment
name|env
parameter_list|)
block|{
name|this
operator|.
name|env
operator|=
name|env
expr_stmt|;
name|if_expr
operator|=
name|if_expr
operator|.
name|traverse
argument_list|(
name|env
argument_list|)
expr_stmt|;
name|then_expr
operator|=
name|then_expr
operator|.
name|traverse
argument_list|(
name|env
argument_list|)
expr_stmt|;
if|if
condition|(
name|else_expr
operator|!=
literal|null
condition|)
block|{
name|else_expr
operator|=
name|else_expr
operator|.
name|traverse
argument_list|(
name|env
argument_list|)
expr_stmt|;
block|}
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

