begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTLetExpr.java */
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
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|BasicType
import|;
end_import

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
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|ISTORE
import|;
end_import

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
name|generic
operator|.
name|InstructionHandle
import|;
end_import

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
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|LocalVariableGen
import|;
end_import

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
name|generic
operator|.
name|MethodGen
import|;
end_import

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
name|generic
operator|.
name|Type
import|;
end_import

begin_comment
comment|/**  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|ASTLetExpr
extends|extends
name|ASTExpr
implements|implements
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|Constants
block|{
specifier|private
name|ASTIdent
index|[]
name|idents
decl_stmt|;
specifier|private
name|ASTExpr
index|[]
name|exprs
decl_stmt|;
specifier|private
name|ASTExpr
name|body
decl_stmt|;
comment|// Generated methods
name|ASTLetExpr
parameter_list|(
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
name|ASTLetExpr
parameter_list|(
name|MiniParser
name|p
parameter_list|,
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
specifier|public
specifier|static
name|Node
name|jjtCreate
parameter_list|(
name|MiniParser
name|p
parameter_list|,
name|int
name|id
parameter_list|)
block|{
return|return
operator|new
name|ASTLetExpr
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
comment|/**    * Overrides ASTExpr.closeNode()    * Cast children nodes to appropiate types.    */
annotation|@
name|Override
specifier|public
name|void
name|closeNode
parameter_list|()
block|{
name|int
name|i
decl_stmt|,
name|len_2
init|=
name|children
operator|.
name|length
operator|/
literal|2
decl_stmt|;
comment|/* length must be a multiple of                                           * two (ident = expr) + 1 (body expr) */
name|idents
operator|=
operator|new
name|ASTIdent
index|[
name|len_2
index|]
expr_stmt|;
name|exprs
operator|=
operator|new
name|ASTExpr
index|[
name|len_2
index|]
expr_stmt|;
comment|// At least one assignment is enforced by the grammar
for|for
control|(
name|i
operator|=
literal|0
init|;
name|i
operator|<
name|len_2
condition|;
name|i
operator|++
control|)
block|{
name|idents
index|[
name|i
index|]
operator|=
operator|(
name|ASTIdent
operator|)
name|children
index|[
name|i
operator|*
literal|2
index|]
expr_stmt|;
name|exprs
index|[
name|i
index|]
operator|=
operator|(
name|ASTExpr
operator|)
name|children
index|[
name|i
operator|*
literal|2
operator|+
literal|1
index|]
expr_stmt|;
block|}
name|body
operator|=
operator|(
name|ASTExpr
operator|)
name|children
index|[
name|children
operator|.
name|length
operator|-
literal|1
index|]
expr_stmt|;
comment|// Last expr is the body
name|children
operator|=
literal|null
expr_stmt|;
comment|// Throw away old reference
block|}
comment|/**    * Overrides ASTExpr.traverse()    */
annotation|@
name|Override
specifier|public
name|ASTExpr
name|traverse
parameter_list|(
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
comment|// Traverse RHS exprs first, so no references to LHS vars are allowed
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|exprs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|exprs
index|[
name|i
index|]
operator|=
name|exprs
index|[
name|i
index|]
operator|.
name|traverse
argument_list|(
operator|(
name|Environment
operator|)
name|env
operator|.
name|clone
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Put argument names into hash table aka. environment
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|idents
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|ASTIdent
name|id
init|=
name|idents
index|[
name|i
index|]
decl_stmt|;
name|String
name|name
init|=
name|id
operator|.
name|getName
argument_list|()
decl_stmt|;
name|EnvEntry
name|entry
init|=
name|env
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|id
operator|.
name|getLine
argument_list|()
argument_list|,
name|id
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Redeclaration of "
operator|+
name|entry
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|env
operator|.
name|put
argument_list|(
operator|new
name|Variable
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|body
operator|=
name|body
operator|.
name|traverse
argument_list|(
name|env
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
comment|/**    * Second pass    * Overrides AstExpr.eval()    * @return type of expression    * @param expected type    */
annotation|@
name|Override
specifier|public
name|int
name|eval
parameter_list|(
name|int
name|expected
parameter_list|)
block|{
comment|//is_simple = true;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|idents
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|int
name|t
init|=
name|exprs
index|[
name|i
index|]
operator|.
name|eval
argument_list|(
name|T_UNKNOWN
argument_list|)
decl_stmt|;
name|idents
index|[
name|i
index|]
operator|.
name|setType
argument_list|(
name|t
argument_list|)
expr_stmt|;
comment|//      is_simple = is_simple&& exprs[i].isSimple();
block|}
return|return
name|type
operator|=
name|body
operator|.
name|eval
argument_list|(
name|expected
argument_list|)
return|;
block|}
comment|/**    * Fifth pass, produce Java code.    */
annotation|@
name|Override
specifier|public
name|void
name|code
parameter_list|(
name|StringBuffer
name|buf
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|idents
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|ident
init|=
name|idents
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
decl_stmt|;
name|int
name|t
init|=
name|idents
index|[
name|i
index|]
operator|.
name|getType
argument_list|()
decl_stmt|;
comment|// can only be int
comment|/* Idents have to be declared at start of function for later use.        * Each name is unique, so there shouldn't be a problem in application.        */
name|exprs
index|[
name|i
index|]
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
literal|"    "
operator|+
name|TYPE_NAMES
index|[
name|t
index|]
operator|+
literal|" "
operator|+
name|ident
operator|+
literal|" = "
operator|+
name|ASTFunDecl
operator|.
name|pop
argument_list|()
operator|+
literal|";\n"
argument_list|)
expr_stmt|;
block|}
name|body
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
block|}
comment|/**    * Fifth pass, produce Java byte code.    */
annotation|@
name|Override
specifier|public
name|void
name|byte_code
parameter_list|(
name|InstructionList
name|il
parameter_list|,
name|MethodGen
name|method
parameter_list|,
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
name|int
name|size
init|=
name|idents
operator|.
name|length
decl_stmt|;
name|LocalVariableGen
index|[]
name|l
init|=
operator|new
name|LocalVariableGen
index|[
name|size
index|]
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
name|size
condition|;
name|i
operator|++
control|)
block|{
name|String
name|ident
init|=
name|idents
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
decl_stmt|;
name|Variable
name|entry
init|=
operator|(
name|Variable
operator|)
name|env
operator|.
name|get
argument_list|(
name|ident
argument_list|)
decl_stmt|;
name|Type
name|t
init|=
name|BasicType
operator|.
name|getType
argument_list|(
operator|(
name|byte
operator|)
name|idents
index|[
name|i
index|]
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
name|LocalVariableGen
name|lg
init|=
name|method
operator|.
name|addLocalVariable
argument_list|(
name|ident
argument_list|,
name|t
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|int
name|slot
init|=
name|lg
operator|.
name|getIndex
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setLocalVariable
argument_list|(
name|lg
argument_list|)
expr_stmt|;
name|InstructionHandle
name|start
init|=
name|il
operator|.
name|getEnd
argument_list|()
decl_stmt|;
name|exprs
index|[
name|i
index|]
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
name|start
operator|=
operator|(
name|start
operator|==
literal|null
operator|)
condition|?
name|il
operator|.
name|getStart
argument_list|()
else|:
name|start
operator|.
name|getNext
argument_list|()
expr_stmt|;
name|lg
operator|.
name|setStart
argument_list|(
name|start
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|ISTORE
argument_list|(
name|slot
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
name|l
index|[
name|i
index|]
operator|=
name|lg
expr_stmt|;
block|}
name|body
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
name|InstructionHandle
name|end
init|=
name|il
operator|.
name|getEnd
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
name|size
condition|;
name|i
operator|++
control|)
block|{
name|l
index|[
name|i
index|]
operator|.
name|setEnd
argument_list|(
name|end
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
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
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|idents
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|idents
index|[
name|i
index|]
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
name|exprs
index|[
name|i
index|]
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
block|}
name|body
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
end_class

end_unit

