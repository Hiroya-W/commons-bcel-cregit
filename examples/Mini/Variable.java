begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|LocalVariableGen
import|;
end_import

begin_comment
comment|/**  * Represents a variable declared in a LET expression or a FUN declaration.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|Variable
implements|implements
name|EnvEntry
block|{
specifier|private
name|ASTIdent
name|name
decl_stmt|;
comment|// Reference to the original declaration
specifier|private
name|boolean
name|reserved
decl_stmt|;
comment|// Is a key word?
specifier|private
name|int
name|line
decl_stmt|,
name|column
decl_stmt|;
comment|// Extracted from name.getToken()
specifier|private
name|String
name|var_name
decl_stmt|;
comment|// Short for name.getName()
specifier|private
name|LocalVariableGen
name|local_var
decl_stmt|;
comment|// local var associated with this variable
specifier|public
name|Variable
parameter_list|(
name|ASTIdent
name|name
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Variable
parameter_list|(
name|ASTIdent
name|name
parameter_list|,
name|boolean
name|reserved
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|reserved
operator|=
name|reserved
expr_stmt|;
name|var_name
operator|=
name|name
operator|.
name|getName
argument_list|()
expr_stmt|;
name|line
operator|=
name|name
operator|.
name|getLine
argument_list|()
expr_stmt|;
name|column
operator|=
name|name
operator|.
name|getColumn
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
operator|!
name|reserved
condition|)
return|return
name|var_name
operator|+
literal|" declared at line "
operator|+
name|line
operator|+
literal|", column "
operator|+
name|column
return|;
else|else
return|return
name|var_name
operator|+
literal|"<reserved key word>"
return|;
block|}
specifier|public
name|ASTIdent
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|String
name|getHashKey
parameter_list|()
block|{
return|return
name|var_name
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
name|getColumn
parameter_list|()
block|{
return|return
name|column
return|;
block|}
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|name
operator|.
name|getType
argument_list|()
return|;
block|}
name|void
name|setLocalVariable
parameter_list|(
name|LocalVariableGen
name|local_var
parameter_list|)
block|{
name|this
operator|.
name|local_var
operator|=
name|local_var
expr_stmt|;
block|}
name|LocalVariableGen
name|getLocalVariable
parameter_list|()
block|{
return|return
name|local_var
return|;
block|}
block|}
end_class

end_unit

