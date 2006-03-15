begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTIdent.java */
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
name|ILOAD
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
name|LocalVariableGen
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
comment|/**  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|ASTIdent
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
name|String
name|name
decl_stmt|;
specifier|private
name|Variable
name|reference
decl_stmt|;
comment|// Reference in environment to decl of this ident
comment|// Generated methods
name|ASTIdent
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
name|ASTIdent
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
name|ASTIdent
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
specifier|public
name|ASTIdent
parameter_list|(
name|String
name|name
parameter_list|,
name|int
name|type
parameter_list|,
name|int
name|line
parameter_list|,
name|int
name|column
parameter_list|)
block|{
name|super
argument_list|(
name|line
argument_list|,
name|column
argument_list|,
name|JJTIDENT
argument_list|)
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|// closeNode, dump inherited
comment|/**    * @return identifier and line/column number of appearance    */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
operator|+
literal|" = "
operator|+
name|name
return|;
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
operator|==
literal|null
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
literal|"Undeclared identifier "
operator|+
name|name
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|entry
operator|instanceof
name|Function
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
literal|"Function "
operator|+
name|name
operator|+
literal|" used as an identifier."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|reference
operator|=
operator|(
name|Variable
operator|)
name|entry
expr_stmt|;
block|}
return|return
name|this
return|;
comment|// Nothing to reduce/traverse further here
block|}
comment|/**    * Overrides AstExpr.eval()    */
specifier|public
name|int
name|eval
parameter_list|(
name|int
name|expected
parameter_list|)
block|{
name|ASTIdent
name|ident
init|=
name|reference
operator|.
name|getName
argument_list|()
decl_stmt|;
name|int
name|t
init|=
name|ident
operator|.
name|getType
argument_list|()
decl_stmt|;
name|is_simple
operator|=
literal|true
expr_stmt|;
comment|// (Very) simple expression, always true
if|if
condition|(
operator|(
name|t
operator|==
name|T_UNKNOWN
operator|)
operator|&&
operator|(
name|expected
operator|==
name|T_UNKNOWN
operator|)
condition|)
block|{
name|type
operator|=
name|T_UNKNOWN
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|t
operator|==
name|T_UNKNOWN
operator|)
operator|&&
operator|(
name|expected
operator|!=
name|T_UNKNOWN
operator|)
condition|)
block|{
name|ident
operator|.
name|setType
argument_list|(
name|expected
argument_list|)
expr_stmt|;
name|type
operator|=
name|expected
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|t
operator|!=
name|T_UNKNOWN
operator|)
operator|&&
operator|(
name|expected
operator|==
name|T_UNKNOWN
operator|)
condition|)
block|{
name|ident
operator|.
name|setType
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|type
operator|=
name|t
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
name|t
expr_stmt|;
comment|// Caller has to check for an error, i.e. t != expected
block|}
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
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"TRUE"
argument_list|)
condition|)
block|{
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"FALSE"
argument_list|)
condition|)
block|{
name|ASTFunDecl
operator|.
name|push
argument_list|(
name|buf
argument_list|,
literal|"0"
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
name|name
argument_list|)
expr_stmt|;
block|}
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
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"TRUE"
argument_list|)
condition|)
block|{
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
block|}
if|else if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"FALSE"
argument_list|)
condition|)
block|{
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
expr_stmt|;
block|}
else|else
block|{
name|LocalVariableGen
name|local_var
init|=
name|reference
operator|.
name|getLocalVariable
argument_list|()
decl_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|ILOAD
argument_list|(
name|local_var
operator|.
name|getIndex
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ASTFunDecl
operator|.
name|push
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
block|}
end_class

end_unit

