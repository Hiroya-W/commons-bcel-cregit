begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|*
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
name|*
import|;
end_import

begin_comment
comment|/**  * Read class file(s) and display its contents. The command line usage is:  *  *<pre>java listclass [-constants] [-code] [-brief] [-dependencies] [-nocontents] [-recurse] class... [-exclude<list>]</pre>  * where  *<ul>  *<li><tt>-code</tt> List byte code of methods</li>  *<li><tt>-brief</tt> List byte codes briefly</li>  *<li><tt>-constants</tt> Print constants table (constant pool)</li>  *<li><tt>-recurse</tt>  Usually intended to be used along with  *<tt>-dependencies</tt>  When this flag is set, listclass will also print information  * about all classes which the target class depends on.</li>  *   *<li><tt>-dependencies</tt>  Setting this flag makes listclass print a list of all  * classes which the target class depends on.  Generated from getting all  * CONSTANT_Class constants from the constant pool.</li>  *   *<li><tt>-exclude</tt>  All non-flag arguments after this flag are added to an  * 'exclusion list'.  Target classes are compared with the members of the  * exclusion list.  Any target class whose fully qualified name begins with a  * name in the exclusion list will not be analyzed/listed.  This is meant  * primarily when using both<tt>-recurse</tt> to exclude java, javax, and sun classes,  * and is recommended as otherwise the output from<tt>-recurse</tt> gets quite long and  * most of it is not interesting.  Note that<tt>-exclude</tt> prevents listing of  * classes, it does not prevent class names from being printed in the  *<tt>-dependencies</tt> list.</li>  *<li><tt>-nocontents</tt> Do not print JavaClass.toString() for the class. I added  * this because sometimes I'm only interested in dependency information.</li>  *</ul>  *<p>Here's a couple examples of how I typically use listclass:<br>  *<pre>java listclass -code MyClass</pre>  * Print information about the class and the byte code of the methods  *<pre>java listclass -nocontents -dependencies MyClass</pre>  * Print a list of all classes which MyClass depends on.  *<pre>java listclass -nocontents -recurse MyClass -exclude java. javax. sun.</pre>  * Print a recursive listing of all classes which MyClass depends on.  Do not  * analyze classes beginning with "java.", "javax.", or "sun.".  *<pre>java listclass -nocontents -dependencies -recurse MyClass -exclude java.javax. sun.</pre>  * Print a recursive listing of dependency information for MyClass and its  * dependents.  Do not analyze classes beginning with "java.", "javax.", or "sun."  *</p>  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>,  *<a href="mailto:twheeler@objectspace.com">Thomas Wheeler</A>  */
end_comment

begin_class
specifier|public
class|class
name|listclass
block|{
name|boolean
name|code
decl_stmt|,
name|constants
decl_stmt|,
name|verbose
decl_stmt|,
name|classdep
decl_stmt|,
name|nocontents
decl_stmt|,
name|recurse
decl_stmt|;
name|Hashtable
name|listedClasses
decl_stmt|;
name|Vector
name|exclude_name
decl_stmt|;
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|argv
parameter_list|)
block|{
name|Vector
name|file_name
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|Vector
name|exclude_name
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|boolean
name|code
init|=
literal|false
decl_stmt|,
name|constants
init|=
literal|false
decl_stmt|,
name|verbose
init|=
literal|true
decl_stmt|,
name|classdep
init|=
literal|false
decl_stmt|,
name|nocontents
init|=
literal|false
decl_stmt|,
name|recurse
init|=
literal|false
decl_stmt|,
name|exclude
init|=
literal|false
decl_stmt|;
name|String
name|name
init|=
literal|null
decl_stmt|;
comment|/* Parse command line arguments.      */
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|argv
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'-'
condition|)
block|{
comment|// command line switch
if|if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-constants"
argument_list|)
condition|)
name|constants
operator|=
literal|true
expr_stmt|;
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-code"
argument_list|)
condition|)
name|code
operator|=
literal|true
expr_stmt|;
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-brief"
argument_list|)
condition|)
name|verbose
operator|=
literal|false
expr_stmt|;
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-dependencies"
argument_list|)
condition|)
name|classdep
operator|=
literal|true
expr_stmt|;
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-nocontents"
argument_list|)
condition|)
name|nocontents
operator|=
literal|true
expr_stmt|;
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-recurse"
argument_list|)
condition|)
name|recurse
operator|=
literal|true
expr_stmt|;
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-exclude"
argument_list|)
condition|)
name|exclude
operator|=
literal|true
expr_stmt|;
if|else if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-help"
argument_list|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Usage: java listclass [-constants] [-code] [-brief] "
operator|+
literal|"[-dependencies] [-nocontents] [-recurse] class... "
operator|+
literal|"[-exclude<list>]\n"
operator|+
literal|"-constants       Print constants table (constant pool)\n"
operator|+
literal|"-code            Dump byte code of methods\n"
operator|+
literal|"-brief           Brief listing\n"
operator|+
literal|"-dependencies    Show class dependencies\n"
operator|+
literal|"-nocontents      Do not print field/method information\n"
operator|+
literal|"-recurse         Recurse into dependent classes\n"
operator|+
literal|"-exclude<list>  Do not list classes beginning with "
operator|+
literal|"strings in<list>"
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Unknown switch "
operator|+
name|argv
index|[
name|i
index|]
operator|+
literal|" ignored."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// add file name to list
if|if
condition|(
name|exclude
condition|)
name|exclude_name
operator|.
name|addElement
argument_list|(
name|argv
index|[
name|i
index|]
argument_list|)
expr_stmt|;
else|else
name|file_name
operator|.
name|addElement
argument_list|(
name|argv
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|file_name
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"list: No input files specified"
argument_list|)
expr_stmt|;
else|else
block|{
name|listclass
name|listClass
init|=
operator|new
name|listclass
argument_list|(
name|code
argument_list|,
name|constants
argument_list|,
name|verbose
argument_list|,
name|classdep
argument_list|,
name|nocontents
argument_list|,
name|recurse
argument_list|,
name|exclude_name
argument_list|)
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
name|file_name
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|name
operator|=
operator|(
name|String
operator|)
name|file_name
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|listClass
operator|.
name|list
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|listclass
parameter_list|(
name|boolean
name|code
parameter_list|,
name|boolean
name|constants
parameter_list|,
name|boolean
name|verbose
parameter_list|,
name|boolean
name|classdep
parameter_list|,
name|boolean
name|nocontents
parameter_list|,
name|boolean
name|recurse
parameter_list|,
name|Vector
name|exclude_name
parameter_list|)
block|{
name|this
operator|.
name|code
operator|=
name|code
expr_stmt|;
name|this
operator|.
name|constants
operator|=
name|constants
expr_stmt|;
name|this
operator|.
name|verbose
operator|=
name|verbose
expr_stmt|;
name|this
operator|.
name|classdep
operator|=
name|classdep
expr_stmt|;
name|this
operator|.
name|nocontents
operator|=
name|nocontents
expr_stmt|;
name|this
operator|.
name|recurse
operator|=
name|recurse
expr_stmt|;
name|this
operator|.
name|listedClasses
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|this
operator|.
name|exclude_name
operator|=
name|exclude_name
expr_stmt|;
block|}
comment|/** Print the given class on screen    */
specifier|public
name|void
name|list
parameter_list|(
name|String
name|name
parameter_list|)
block|{
try|try
block|{
name|JavaClass
name|java_class
decl_stmt|;
if|if
condition|(
operator|(
name|listedClasses
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|!=
literal|null
operator|)
operator|||
name|name
operator|.
name|startsWith
argument_list|(
literal|"["
argument_list|)
condition|)
return|return;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|exclude_name
operator|.
name|size
argument_list|()
condition|;
name|idx
operator|++
control|)
if|if
condition|(
name|name
operator|.
name|startsWith
argument_list|(
operator|(
name|String
operator|)
name|exclude_name
operator|.
name|elementAt
argument_list|(
name|idx
argument_list|)
argument_list|)
condition|)
return|return;
if|if
condition|(
name|name
operator|.
name|endsWith
argument_list|(
literal|".class"
argument_list|)
condition|)
block|{
name|java_class
operator|=
operator|new
name|ClassParser
argument_list|(
name|name
argument_list|)
operator|.
name|parse
argument_list|()
expr_stmt|;
comment|// May throw IOException
block|}
else|else
block|{
name|java_class
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|nocontents
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|java_class
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|java_class
argument_list|)
expr_stmt|;
comment|// Dump the contents
if|if
condition|(
name|constants
condition|)
comment|// Dump the constant pool ?
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|java_class
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|code
condition|)
comment|// Dump the method code ?
name|printCode
argument_list|(
name|java_class
operator|.
name|getMethods
argument_list|()
argument_list|,
name|verbose
argument_list|)
expr_stmt|;
if|if
condition|(
name|classdep
condition|)
name|printClassDependencies
argument_list|(
name|java_class
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
name|listedClasses
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|recurse
condition|)
block|{
name|String
index|[]
name|dependencies
init|=
name|getClassDependencies
argument_list|(
name|java_class
operator|.
name|getConstantPool
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|dependencies
operator|.
name|length
condition|;
name|idx
operator|++
control|)
name|list
argument_list|(
name|dependencies
index|[
name|idx
index|]
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Error loading class "
operator|+
name|name
operator|+
literal|" ("
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Error processing class "
operator|+
name|name
operator|+
literal|" ("
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**    * Dump the list of classes this class is dependent on    */
specifier|public
specifier|static
name|void
name|printClassDependencies
parameter_list|(
name|ConstantPool
name|pool
parameter_list|)
block|{
name|String
index|[]
name|names
init|=
name|getClassDependencies
argument_list|(
name|pool
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Dependencies:"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|names
operator|.
name|length
condition|;
name|idx
operator|++
control|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\t"
operator|+
name|names
index|[
name|idx
index|]
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|String
index|[]
name|getClassDependencies
parameter_list|(
name|ConstantPool
name|pool
parameter_list|)
block|{
name|String
index|[]
name|tempArray
init|=
operator|new
name|String
index|[
name|pool
operator|.
name|getLength
argument_list|()
index|]
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|pool
operator|.
name|getLength
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|Constant
name|c
init|=
name|pool
operator|.
name|getConstant
argument_list|(
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
name|c
operator|.
name|getTag
argument_list|()
operator|==
name|Constants
operator|.
name|CONSTANT_Class
condition|)
block|{
name|ConstantUtf8
name|c1
init|=
operator|(
name|ConstantUtf8
operator|)
name|pool
operator|.
name|getConstant
argument_list|(
operator|(
operator|(
name|ConstantClass
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
argument_list|)
decl_stmt|;
name|buf
operator|.
name|setLength
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
operator|new
name|String
argument_list|(
name|c1
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|n
init|=
literal|0
init|;
name|n
operator|<
name|buf
operator|.
name|length
argument_list|()
condition|;
name|n
operator|++
control|)
block|{
if|if
condition|(
name|buf
operator|.
name|charAt
argument_list|(
name|n
argument_list|)
operator|==
literal|'/'
condition|)
name|buf
operator|.
name|setCharAt
argument_list|(
name|n
argument_list|,
literal|'.'
argument_list|)
expr_stmt|;
block|}
name|tempArray
index|[
name|size
operator|++
index|]
operator|=
name|buf
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
name|String
index|[]
name|dependencies
init|=
operator|new
name|String
index|[
name|size
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|tempArray
argument_list|,
literal|0
argument_list|,
name|dependencies
argument_list|,
literal|0
argument_list|,
name|size
argument_list|)
expr_stmt|;
return|return
name|dependencies
return|;
block|}
comment|/**    * Dump the disassembled code of all methods in the class.    */
specifier|public
specifier|static
name|void
name|printCode
parameter_list|(
name|Method
index|[]
name|methods
parameter_list|,
name|boolean
name|verbose
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
name|methods
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|methods
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|Code
name|code
init|=
name|methods
index|[
name|i
index|]
operator|.
name|getCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|code
operator|!=
literal|null
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|code
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

