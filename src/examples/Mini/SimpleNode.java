begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2004 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");   *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. SimpleNode.java */
end_comment

begin_comment
comment|/* JJT: 0.3pre1 */
end_comment

begin_package
package|package
name|Mini
package|;
end_package

begin_comment
comment|/**  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|SimpleNode
implements|implements
name|Node
block|{
specifier|protected
name|Node
name|parent
decl_stmt|;
specifier|protected
name|Node
index|[]
name|children
decl_stmt|;
specifier|protected
name|int
name|id
decl_stmt|;
specifier|protected
name|MiniParser
name|parser
decl_stmt|;
specifier|public
name|SimpleNode
parameter_list|(
name|int
name|i
parameter_list|)
block|{
name|id
operator|=
name|i
expr_stmt|;
block|}
specifier|public
name|SimpleNode
parameter_list|(
name|MiniParser
name|p
parameter_list|,
name|int
name|i
parameter_list|)
block|{
name|this
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|parser
operator|=
name|p
expr_stmt|;
block|}
specifier|public
name|void
name|jjtOpen
parameter_list|()
block|{
block|}
specifier|public
name|void
name|jjtClose
parameter_list|()
block|{
block|}
specifier|public
name|void
name|closeNode
parameter_list|()
block|{
block|}
specifier|public
name|void
name|jjtSetParent
parameter_list|(
name|Node
name|n
parameter_list|)
block|{
name|parent
operator|=
name|n
expr_stmt|;
block|}
specifier|public
name|Node
name|jjtGetParent
parameter_list|()
block|{
return|return
name|parent
return|;
block|}
specifier|public
name|void
name|jjtAddChild
parameter_list|(
name|Node
name|n
parameter_list|,
name|int
name|i
parameter_list|)
block|{
if|if
condition|(
name|children
operator|==
literal|null
condition|)
block|{
name|children
operator|=
operator|new
name|Node
index|[
name|i
operator|+
literal|1
index|]
expr_stmt|;
block|}
if|else if
condition|(
name|i
operator|>=
name|children
operator|.
name|length
condition|)
block|{
name|Node
name|c
index|[]
init|=
operator|new
name|Node
index|[
name|i
operator|+
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|children
argument_list|,
literal|0
argument_list|,
name|c
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
name|c
expr_stmt|;
block|}
name|children
index|[
name|i
index|]
operator|=
name|n
expr_stmt|;
block|}
specifier|public
name|Node
name|jjtGetChild
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
name|children
index|[
name|i
index|]
return|;
block|}
specifier|public
name|int
name|jjtGetNumChildren
parameter_list|()
block|{
return|return
operator|(
name|children
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|children
operator|.
name|length
return|;
block|}
comment|/* You can override these two methods in subclasses of SimpleNode to      customize the way the node appears when the tree is dumped.  If      your output uses more than one line you should override      toString(String), otherwise overriding toString() is probably all      you need to do. */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|MiniParserTreeConstants
operator|.
name|jjtNodeName
index|[
name|id
index|]
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
return|return
name|prefix
operator|+
name|toString
argument_list|()
return|;
block|}
comment|/* Override this method if you want to customize how the node dumps      out its children. */
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
if|if
condition|(
name|children
operator|!=
literal|null
condition|)
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
name|children
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
name|SimpleNode
name|n
init|=
operator|(
name|SimpleNode
operator|)
name|children
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|n
operator|!=
literal|null
condition|)
block|{
name|n
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
block|}
block|}
end_class

end_unit
