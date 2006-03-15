begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|InstructionConstants
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
comment|/**  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|ASTIfExpr
extends|extends
name|ASTExpr
implements|implements
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
block|{
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
name|ASTIfExpr
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
comment|/**    * Overrides ASTExpr.closeNode()    * Cast children nodes Node[] to appropiate type ASTExpr[]    */
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
comment|/**    * Overrides ASTExpr.traverse()    */
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
comment|/**    * Second pass    * Overrides AstExpr.eval()    * @return type of expression    * @param expected type    */
specifier|public
name|int
name|eval
parameter_list|(
name|int
name|expected
parameter_list|)
block|{
name|int
name|then_type
decl_stmt|,
name|else_type
decl_stmt|,
name|if_type
decl_stmt|;
if|if
condition|(
operator|(
name|if_type
operator|=
name|if_expr
operator|.
name|eval
argument_list|(
name|T_BOOLEAN
argument_list|)
operator|)
operator|!=
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
name|TYPE_NAMES
index|[
name|if_type
index|]
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
name|then_type
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
operator|(
name|expected
operator|!=
name|T_UNKNOWN
operator|)
operator|&&
operator|(
name|then_type
operator|!=
name|expected
operator|)
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
name|TYPE_NAMES
index|[
name|expected
index|]
operator|+
literal|" but "
operator|+
name|TYPE_NAMES
index|[
name|then_type
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
name|else_type
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
operator|(
name|expected
operator|!=
name|T_UNKNOWN
operator|)
operator|&&
operator|(
name|else_type
operator|!=
name|expected
operator|)
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
name|TYPE_NAMES
index|[
name|expected
index|]
operator|+
literal|" but "
operator|+
name|TYPE_NAMES
index|[
name|else_type
index|]
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|then_type
operator|==
name|T_UNKNOWN
condition|)
block|{
name|then_type
operator|=
name|else_type
expr_stmt|;
name|then_expr
operator|.
name|setType
argument_list|(
name|else_type
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|else_type
operator|=
name|then_type
expr_stmt|;
name|else_expr
operator|=
name|then_expr
expr_stmt|;
block|}
if|if
condition|(
name|then_type
operator|!=
name|else_type
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
name|TYPE_NAMES
index|[
name|then_type
index|]
operator|+
literal|" vs. "
operator|+
name|TYPE_NAMES
index|[
name|else_type
index|]
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
name|type
operator|=
name|then_type
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
comment|/**    * Fourth pass, produce Java code.    */
specifier|public
name|void
name|code
parameter_list|(
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
comment|/**    * Fifth pass, produce Java byte code.    */
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
name|InstructionList
name|then_code
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
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
name|InstructionConstants
operator|.
name|NOP
argument_list|)
argument_list|)
expr_stmt|;
comment|// May be optimized away later
block|}
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
block|}
end_class

end_unit

