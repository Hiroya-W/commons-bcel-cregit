begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTExpr.java */
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
name|IF_ICMPEQ
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
name|IF_ICMPGE
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
name|IF_ICMPGT
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
name|IF_ICMPLE
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
name|IF_ICMPLT
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
name|IF_ICMPNE
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
name|PUSH
import|;
end_import

begin_comment
comment|/**  * Represents arithmetic expressions such as '(a + 12 == b) OR c'. The parse tree is built initially by the parser and  * modified, i.e. compacted with 'traverse()'. Each (Expr, Term, Factor) node with kind == -1 is replaced which its  * successor node, which is converted to type 'Expr'  *  * A node with kind == -1 denotes the fact that this expression node has just one child branch and thus may be replaced  * by this branch (or leaf) directly without altering the expression semantics. Term and Factor nodes are used only to  * build the parse tree obeying the aritmetical precedences (* stronger than +, etc.) and are discarded in the first  * pass.  */
end_comment

begin_class
specifier|public
class|class
name|ASTExpr
extends|extends
name|SimpleNode
implements|implements
name|MiniParserConstants
implements|,
name|MiniParserTreeConstants
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
name|ASTExpr
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|String
name|toBool
parameter_list|(
specifier|final
name|String
name|i
parameter_list|)
block|{
return|return
literal|"("
operator|+
name|i
operator|+
literal|" != 0)"
return|;
block|}
specifier|private
specifier|static
name|String
name|toInt
parameter_list|(
specifier|final
name|String
name|i
parameter_list|)
block|{
return|return
literal|"(("
operator|+
name|i
operator|+
literal|")? 1 : 0)"
return|;
block|}
specifier|protected
name|int
name|kind
init|=
operator|-
literal|1
decl_stmt|;
comment|// Single twig to leaf?
specifier|private
name|int
name|unop
init|=
operator|-
literal|1
decl_stmt|;
comment|// Special case: Unary operand applied
specifier|protected
name|ASTExpr
index|[]
name|exprs
decl_stmt|;
comment|// Sub expressions
specifier|protected
name|Environment
name|env
decl_stmt|;
comment|// Needed in all passes
specifier|protected
name|int
name|line
decl_stmt|,
name|column
decl_stmt|;
specifier|protected
name|boolean
name|is_simple
decl_stmt|;
comment|// true, if simple expression like '12 + f(a)'
comment|/*      * Not all children shall inherit this, exceptions are ASTIdent and ASTFunAppl, which look up the type in the      * corresponding environment entry.      */
specifier|protected
name|int
name|type
init|=
name|Const
operator|.
name|T_UNKNOWN
decl_stmt|;
comment|/*      * Special constructor, called from ASTTerm.traverse() and ASTFactor.traverse(), when traverse()ing the parse tree      * replace themselves with Expr nodes.      */
name|ASTExpr
parameter_list|(
specifier|final
name|ASTExpr
index|[]
name|children
parameter_list|,
specifier|final
name|int
name|kind
parameter_list|,
specifier|final
name|int
name|line
parameter_list|,
specifier|final
name|int
name|column
parameter_list|)
block|{
name|this
argument_list|(
name|line
argument_list|,
name|column
argument_list|,
name|kind
argument_list|,
name|JJTEXPR
argument_list|)
expr_stmt|;
name|exprs
operator|=
name|children
expr_stmt|;
block|}
comment|// Generated methods
name|ASTExpr
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
name|ASTExpr
parameter_list|(
specifier|final
name|int
name|line
parameter_list|,
specifier|final
name|int
name|column
parameter_list|,
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
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
name|this
operator|.
name|column
operator|=
name|column
expr_stmt|;
block|}
name|ASTExpr
parameter_list|(
specifier|final
name|int
name|line
parameter_list|,
specifier|final
name|int
name|column
parameter_list|,
specifier|final
name|int
name|kind
parameter_list|,
specifier|final
name|int
name|id
parameter_list|)
block|{
name|this
argument_list|(
name|line
argument_list|,
name|column
argument_list|,
name|id
argument_list|)
expr_stmt|;
name|this
operator|.
name|kind
operator|=
name|kind
expr_stmt|;
block|}
name|ASTExpr
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
name|exprs
index|[
literal|0
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
if|if
condition|(
name|unop
operator|!=
operator|-
literal|1
condition|)
block|{
comment|// Apply unary operand
if|if
condition|(
name|unop
operator|==
name|MINUS
condition|)
block|{
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|INEG
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// == NOT
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|push
argument_list|()
expr_stmt|;
comment|// Push TRUE
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|IXOR
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Apply binary operand
name|BranchHandle
name|bh
init|=
literal|null
decl_stmt|;
name|exprs
index|[
literal|1
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
switch|switch
condition|(
name|kind
condition|)
block|{
case|case
name|PLUS
case|:
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|IADD
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
break|break;
case|case
name|MINUS
case|:
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|ISUB
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
break|break;
case|case
name|MULT
case|:
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|IMUL
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
break|break;
case|case
name|DIV
case|:
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|IDIV
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
break|break;
case|case
name|AND
case|:
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|IAND
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
break|break;
case|case
name|OR
case|:
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|IOR
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|()
expr_stmt|;
break|break;
comment|/* Use negated operands */
case|case
name|EQ
case|:
name|bh
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|IF_ICMPNE
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|LEQ
case|:
name|bh
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|IF_ICMPGT
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|GEQ
case|:
name|bh
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|IF_ICMPLT
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|NEQ
case|:
name|bh
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|IF_ICMPEQ
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|LT
case|:
name|bh
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|IF_ICMPGE
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
case|case
name|GT
case|:
name|bh
operator|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|IF_ICMPLE
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|ASTFunDecl
operator|.
name|pop
argument_list|(
literal|2
argument_list|)
expr_stmt|;
break|break;
default|default:
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Unhandled case: "
operator|+
name|kind
argument_list|)
expr_stmt|;
block|}
switch|switch
condition|(
name|kind
condition|)
block|{
case|case
name|EQ
case|:
case|case
name|LEQ
case|:
case|case
name|GEQ
case|:
case|case
name|NEQ
case|:
case|case
name|LT
case|:
case|case
name|GT
case|:
name|BranchHandle
name|g
decl_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
literal|1
argument_list|)
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
name|bh
operator|.
name|setTarget
argument_list|(
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
literal|0
argument_list|)
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
name|ASTFunDecl
operator|.
name|push
argument_list|()
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
block|}
comment|/**      * Overrides SimpleNode.closeNode(). Overridden by some subclasses.      *      * Called by the parser when the construction of this node is finished. Casts children Node[] to precise ASTExpr[] type.      */
annotation|@
name|Override
specifier|public
name|void
name|closeNode
parameter_list|()
block|{
if|if
condition|(
name|children
operator|!=
literal|null
condition|)
block|{
name|exprs
operator|=
operator|new
name|ASTExpr
index|[
name|children
operator|.
name|length
index|]
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|children
argument_list|,
literal|0
argument_list|,
name|exprs
argument_list|,
literal|0
argument_list|,
name|children
operator|.
name|length
argument_list|)
expr_stmt|;
name|children
operator|=
literal|null
expr_stmt|;
comment|// Throw away old reference
block|}
block|}
comment|/**      * Fourth pass, produce Java code.      */
specifier|public
name|void
name|code
parameter_list|(
specifier|final
name|StringBuffer
name|buf
parameter_list|)
block|{
if|if
condition|(
name|unop
operator|!=
operator|-
literal|1
condition|)
block|{
name|exprs
index|[
literal|0
index|]
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
specifier|final
name|String
name|top
init|=
name|ASTFunDecl
operator|.
name|pop
argument_list|()
decl_stmt|;
if|if
condition|(
name|unop
operator|==
name|MINUS
condition|)
block|{
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
literal|"-"
operator|+
name|top
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
literal|"("
operator|+
name|top
operator|+
literal|" == 1)? 0 : 1)"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|exprs
index|[
literal|0
index|]
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|exprs
index|[
literal|1
index|]
operator|.
name|code
argument_list|(
name|buf
argument_list|)
expr_stmt|;
specifier|final
name|String
name|_body_int2
init|=
name|ASTFunDecl
operator|.
name|pop
argument_list|()
decl_stmt|;
specifier|final
name|String
name|_body_int
init|=
name|ASTFunDecl
operator|.
name|pop
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|kind
condition|)
block|{
case|case
name|PLUS
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|_body_int
operator|+
literal|" + "
operator|+
name|_body_int2
argument_list|)
expr_stmt|;
break|break;
case|case
name|MINUS
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|_body_int
operator|+
literal|" - "
operator|+
name|_body_int2
argument_list|)
expr_stmt|;
break|break;
case|case
name|MULT
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|_body_int
operator|+
literal|" * "
operator|+
name|_body_int2
argument_list|)
expr_stmt|;
break|break;
case|case
name|DIV
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|_body_int
operator|+
literal|" / "
operator|+
name|_body_int2
argument_list|)
expr_stmt|;
break|break;
case|case
name|AND
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|toBool
argument_list|(
name|_body_int
argument_list|)
operator|+
literal|"&& "
operator|+
name|toBool
argument_list|(
name|_body_int2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|OR
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|toBool
argument_list|(
name|_body_int
argument_list|)
operator|+
literal|" || "
operator|+
name|toBool
argument_list|(
name|_body_int2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|EQ
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|_body_int
operator|+
literal|" == "
operator|+
name|_body_int2
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|LEQ
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|_body_int
operator|+
literal|"<= "
operator|+
name|_body_int2
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|GEQ
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|_body_int
operator|+
literal|">= "
operator|+
name|_body_int2
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|NEQ
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|_body_int
operator|+
literal|" != "
operator|+
name|_body_int2
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|LT
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|_body_int
operator|+
literal|"< "
operator|+
name|_body_int2
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|GT
case|:
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
name|toInt
argument_list|(
name|_body_int
operator|+
literal|"> "
operator|+
name|_body_int2
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Unhandled case: "
operator|+
name|kind
argument_list|)
expr_stmt|;
block|}
block|}
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
if|if
condition|(
name|exprs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
specifier|final
name|ASTExpr
name|expr
range|:
name|exprs
control|)
block|{
name|expr
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
block|}
comment|/**      * Second and third pass      *      * @return type of expression      * @param expected type      */
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
name|childType
init|=
name|Const
operator|.
name|T_UNKNOWN
decl_stmt|,
name|t
decl_stmt|;
name|is_simple
operator|=
literal|true
expr_stmt|;
comment|// Determine expected node type depending on used operator.
if|if
condition|(
name|unop
operator|!=
operator|-
literal|1
condition|)
block|{
if|if
condition|(
name|unop
operator|==
name|MINUS
condition|)
block|{
name|childType
operator|=
name|type
operator|=
name|Const
operator|.
name|T_INT
expr_stmt|;
comment|// -
block|}
else|else
block|{
name|childType
operator|=
name|type
operator|=
name|Const
operator|.
name|T_BOOLEAN
expr_stmt|;
comment|// !
block|}
block|}
if|else
comment|// Compute expected type
if|if
condition|(
name|kind
operator|==
name|PLUS
operator|||
name|kind
operator|==
name|MINUS
operator|||
name|kind
operator|==
name|MULT
operator|||
name|kind
operator|==
name|MOD
operator|||
name|kind
operator|==
name|DIV
condition|)
block|{
name|childType
operator|=
name|type
operator|=
name|Const
operator|.
name|T_INT
expr_stmt|;
block|}
if|else if
condition|(
name|kind
operator|==
name|AND
operator|||
name|kind
operator|==
name|OR
condition|)
block|{
name|childType
operator|=
name|type
operator|=
name|Const
operator|.
name|T_BOOLEAN
expr_stmt|;
block|}
else|else
block|{
comment|// LEQ, GT, etc.
name|childType
operator|=
name|Const
operator|.
name|T_INT
expr_stmt|;
name|type
operator|=
name|Const
operator|.
name|T_BOOLEAN
expr_stmt|;
block|}
comment|// Get type of subexpressions
for|for
control|(
specifier|final
name|ASTExpr
name|expr
range|:
name|exprs
control|)
block|{
name|t
operator|=
name|expr
operator|.
name|eval
argument_list|(
name|childType
argument_list|)
expr_stmt|;
if|if
condition|(
name|t
operator|!=
name|childType
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|expr
operator|.
name|getLine
argument_list|()
argument_list|,
name|expr
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Expression has not expected type "
operator|+
name|Const
operator|.
name|getTypeName
argument_list|(
name|childType
argument_list|)
operator|+
literal|" but "
operator|+
name|Const
operator|.
name|getTypeName
argument_list|(
name|t
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
name|is_simple
operator|=
name|is_simple
operator|&&
name|expr
operator|.
name|isSimple
argument_list|()
expr_stmt|;
block|}
return|return
name|type
return|;
block|}
specifier|public
name|int
name|getColumn
parameter_list|()
block|{
return|return
name|column
return|;
block|}
specifier|public
name|int
name|getKind
parameter_list|()
block|{
return|return
name|kind
return|;
block|}
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
name|line
return|;
block|}
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
name|int
name|getUnOp
parameter_list|()
block|{
return|return
name|unop
return|;
block|}
specifier|public
name|boolean
name|isSimple
parameter_list|()
block|{
return|return
name|is_simple
return|;
block|}
specifier|public
name|void
name|setColumn
parameter_list|(
specifier|final
name|int
name|column
parameter_list|)
block|{
name|this
operator|.
name|column
operator|=
name|column
expr_stmt|;
block|}
specifier|public
name|void
name|setKind
parameter_list|(
specifier|final
name|int
name|kind
parameter_list|)
block|{
name|this
operator|.
name|kind
operator|=
name|kind
expr_stmt|;
block|}
specifier|public
name|void
name|setLine
parameter_list|(
specifier|final
name|int
name|line
parameter_list|)
block|{
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
block|}
specifier|public
name|void
name|setPosition
parameter_list|(
specifier|final
name|int
name|line
parameter_list|,
specifier|final
name|int
name|column
parameter_list|)
block|{
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
name|this
operator|.
name|column
operator|=
name|column
expr_stmt|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
specifier|final
name|int
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|void
name|setUnOp
parameter_list|(
specifier|final
name|int
name|unop
parameter_list|)
block|{
name|this
operator|.
name|unop
operator|=
name|unop
expr_stmt|;
block|}
comment|/**      * @return name of node, its kind and the number of children.      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|op
init|=
literal|""
decl_stmt|;
specifier|final
name|int
name|len
init|=
name|children
operator|!=
literal|null
condition|?
name|children
operator|.
name|length
else|:
literal|0
decl_stmt|;
if|if
condition|(
name|unop
operator|!=
operator|-
literal|1
condition|)
block|{
name|op
operator|=
name|tokenImage
index|[
name|unop
index|]
expr_stmt|;
block|}
if|else if
condition|(
name|kind
operator|!=
operator|-
literal|1
condition|)
block|{
name|op
operator|=
name|tokenImage
index|[
name|kind
index|]
expr_stmt|;
block|}
return|return
name|jjtNodeName
index|[
name|id
index|]
operator|+
literal|"("
operator|+
name|op
operator|+
literal|")["
operator|+
name|len
operator|+
literal|"]<"
operator|+
name|Const
operator|.
name|getTypeName
argument_list|(
name|type
argument_list|)
operator|+
literal|"> @"
operator|+
name|line
operator|+
literal|", "
operator|+
name|column
return|;
block|}
comment|/**      * First pass Overridden by subclasses. Traverse the whole parse tree recursively and drop redundant nodes.      */
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
if|if
condition|(
name|kind
operator|==
operator|-
literal|1
operator|&&
name|unop
operator|==
operator|-
literal|1
condition|)
block|{
return|return
name|exprs
index|[
literal|0
index|]
operator|.
name|traverse
argument_list|(
name|env
argument_list|)
return|;
comment|// --> Replaced by successor
block|}
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
name|env
argument_list|)
expr_stmt|;
comment|// References may change
block|}
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

