begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
operator|.
name|statics
package|;
end_package

begin_comment
comment|/* ====================================================================  * The Apache Software License, Version 1.1  *  * Copyright (c) 2001 The Apache Software Foundation.  All rights  * reserved.  *  * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions  * are met:  *  * 1. Redistributions of source code must retain the above copyright  *    notice, this list of conditions and the following disclaimer.  *  * 2. Redistributions in binary form must reproduce the above copyright  *    notice, this list of conditions and the following disclaimer in  *    the documentation and/or other materials provided with the  *    distribution.  *  * 3. The end-user documentation included with the redistribution,  *    if any, must include the following acknowledgment:  *       "This product includes software developed by the  *        Apache Software Foundation (http://www.apache.org/)."  *    Alternately, this acknowledgment may appear in the software itself,  *    if and wherever such third-party acknowledgments normally appear.  *  * 4. The names "Apache" and "Apache Software Foundation" and  *    "Apache BCEL" must not be used to endorse or promote products  *    derived from this software without prior written permission. For  *    written permission, please contact apache@apache.org.  *  * 5. Products derived from this software may not be called "Apache",  *    "Apache BCEL", nor may "Apache" appear in their name, without  *    prior written permission of the Apache Software Foundation.  *  * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED  * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES  * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR  * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF  * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT  * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF  * SUCH DAMAGE.  * ====================================================================  *  * This software consists of voluntary contributions made by many  * individuals on behalf of the Apache Software Foundation.  For more  * information on the Apache Software Foundation, please see  *<http://www.apache.org/>.  */
end_comment

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
name|Repository
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
name|verifier
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
name|verifier
operator|.
name|exc
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
name|verifier
operator|.
name|exc
operator|.
name|Utility
import|;
end_import

begin_comment
comment|/**  * This PassVerifier verifies a class file according to pass 1 as  * described in The Java Virtual Machine Specification, 2nd edition.  * More detailed information is to be found at the do_verify() method's  * documentation.  *  * @version $Id$  * @author<A HREF="http://www.inf.fu-berlin.de/~ehaase"/>Enver Haase</A>  * @see #do_verify()  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Pass1Verifier
extends|extends
name|PassVerifier
block|{
comment|/** 	 * DON'T USE THIS EVEN PRIVATELY! USE getJavaClass() INSTEAD. 	 * @see #getJavaClass() 	 */
specifier|private
name|JavaClass
name|jc
decl_stmt|;
comment|/** 	 * The Verifier that created this. 	 */
specifier|private
name|Verifier
name|myOwner
decl_stmt|;
comment|/** Used to load in and return the myOwner-matching JavaClass object when needed. Avoids loading in a class file when it's not really needed! */
specifier|private
name|JavaClass
name|getJavaClass
parameter_list|()
block|{
if|if
condition|(
name|jc
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|jc
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|myOwner
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
comment|// FIXME: currently, Pass1Verifier treats jc == null as a special
comment|// case, so we don't need to do anything here.  A better solution
comment|// would be to simply throw the ClassNotFoundException
comment|// out of this method.
block|}
block|}
return|return
name|jc
return|;
block|}
comment|/** 	 * Should only be instantiated by a Verifier. 	 * 	 * @see org.apache.bcel.verifier.Verifier 	 */
specifier|public
name|Pass1Verifier
parameter_list|(
name|Verifier
name|owner
parameter_list|)
block|{
name|myOwner
operator|=
name|owner
expr_stmt|;
block|}
comment|/** 	 * Pass-one verification basically means loading in a class file. 	 * The Java Virtual Machine Specification is not too precise about 	 * what makes the difference between passes one and two. 	 * The answer is that only pass one is performed on a class file as 	 * long as its resolution is not requested; whereas pass two and 	 * pass three are performed during the resolution process. 	 * Only four constraints to be checked are explicitely stated by 	 * The Java Virtual Machine Specification, 2nd edition: 	 *<UL> 	 *<LI>The first four bytes must contain the right magic number (0xCAFEBABE). 	 *<LI>All recognized attributes must be of the proper length. 	 *<LI>The class file must not be truncated or have extra bytes at the end. 	 *<LI>The constant pool must not contain any superficially unrecognizable information. 	 *</UL> 	 * A more in-depth documentation of what pass one should do was written by 	 *<A HREF=mailto:pwfong@cs.sfu.ca>Philip W. L. Fong</A>: 	 *<UL> 	 *<LI> the file should not be truncated. 	 *<LI> the file should not have extra bytes at the end. 	 *<LI> all variable-length structures should be well-formatted: 	 *<UL> 	 *<LI> there should only be constant_pool_count-1 many entries in the constant pool. 	 *<LI> all constant pool entries should have size the same as indicated by their type tag. 	 *<LI> there are exactly interfaces_count many entries in the interfaces array of the class file. 	 *<LI> there are exactly fields_count many entries in the fields array of the class file. 	 *<LI> there are exactly methods_count many entries in the methods array of the class file. 	 *<LI> there are exactly attributes_count many entries in the attributes array of the class file, fields, methods, and code attribute. 	 *<LI> there should be exactly attribute_length many bytes in each attribute. Inconsistency between attribute_length and the actually size of the attribute content should be uncovered. For example, in an Exceptions attribute, the actual number of exceptions as required by the number_of_exceptions field might yeild an attribute size that doesn't match the attribute_length. Such an anomaly should be detected. 	 *<LI> all attributes should have proper length. In particular, under certain context (e.g. while parsing method_info), recognizable attributes (e.g. "Code" attribute) should have correct format (e.g. attribute_length is 2). 	 *</UL> 	 *<LI> Also, certain constant values are checked for validity: 	 *<UL> 	 *<LI> The magic number should be 0xCAFEBABE. 	 *<LI> The major and minor version numbers are valid. 	 *<LI> All the constant pool type tags are recognizable. 	 *<LI> All undocumented access flags are masked off before use. Strictly speaking, this is not really a check. 	 *<LI> The field this_class should point to a string that represents a legal non-array class name, and this name should be the same as the class file being loaded. 	 *<LI> the field super_class should point to a string that represents a legal non-array class name. 	 *<LI> Because some of the above checks require cross referencing the constant pool entries, guards are set up to make sure that the referenced entries are of the right type and the indices are within the legal range (0< index< constant_pool_count). 	 *</UL> 	 *<LI> Extra checks done in pass 1: 	 *<UL> 	 *<LI> the constant values of static fields should have the same type as the fields. 	 *<LI> the number of words in a parameter list does not exceed 255 and locals_max. 	 *<LI> the name and signature of fields and methods are verified to be of legal format. 	 *</UL> 	 *</UL> 	 * (From the Paper<A HREF=http://www.cs.sfu.ca/people/GradStudents/pwfong/personal/JVM/pass1/>The Mysterious Pass One, first draft, September 2, 1997</A>.) 	 *</BR> 	 * However, most of this is done by parsing a class file or generating a class file into BCEL's internal data structure. 	 *<B>Therefore, all that is really done here is look up the class file from BCEL's repository.</B> 	 * This is also motivated by the fact that some omitted things 	 * (like the check for extra bytes at the end of the class file) are handy when actually using BCEL to repair a class file (otherwise you would not be 	 * able to load it into BCEL). 	 * 	 * @see org.apache.bcel.Repository 	 */
specifier|public
name|VerificationResult
name|do_verify
parameter_list|()
block|{
name|JavaClass
name|jc
decl_stmt|;
try|try
block|{
name|jc
operator|=
name|getJavaClass
argument_list|()
expr_stmt|;
comment|//loads in the class file if not already done.
if|if
condition|(
name|jc
operator|!=
literal|null
condition|)
block|{
comment|/* If we find more constraints to check, we should do this in an own method. */
if|if
condition|(
operator|!
name|myOwner
operator|.
name|getClassName
argument_list|()
operator|.
name|equals
argument_list|(
name|jc
operator|.
name|getClassName
argument_list|()
argument_list|)
condition|)
block|{
comment|// This should maybe caught by BCEL: In case of renamed .class files we get wrong
comment|// JavaClass objects here.
throw|throw
operator|new
name|LoadingException
argument_list|(
literal|"Wrong name: the internal name of the .class file '"
operator|+
name|jc
operator|.
name|getClassName
argument_list|()
operator|+
literal|"' does not match the file's name '"
operator|+
name|myOwner
operator|.
name|getClassName
argument_list|()
operator|+
literal|"'."
argument_list|)
throw|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|LoadingException
name|e
parameter_list|)
block|{
return|return
operator|new
name|VerificationResult
argument_list|(
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ClassFormatException
name|e
parameter_list|)
block|{
return|return
operator|new
name|VerificationResult
argument_list|(
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
comment|// BCEL does not catch every possible RuntimeException; e.g. if
comment|// a constant pool index is referenced that does not exist.
return|return
operator|new
name|VerificationResult
argument_list|(
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
argument_list|,
literal|"Parsing via BCEL did not succeed. "
operator|+
name|e
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" occured:\n"
operator|+
name|Utility
operator|.
name|getStackTrace
argument_list|(
name|e
argument_list|)
argument_list|)
return|;
block|}
if|if
condition|(
name|jc
operator|!=
literal|null
condition|)
block|{
return|return
name|VerificationResult
operator|.
name|VR_OK
return|;
block|}
else|else
block|{
comment|//TODO: Maybe change Repository's behaviour to throw a LoadingException instead of just returning "null"
comment|//      if a class file cannot be found or in another way be looked up.
return|return
operator|new
name|VerificationResult
argument_list|(
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
argument_list|,
literal|"Repository.lookup() failed. FILE NOT FOUND?"
argument_list|)
return|;
block|}
block|}
comment|/** 	 * Currently this returns an empty array of String. 	 * One could parse the error messages of BCEL 	 * (written to java.lang.System.err) when loading 	 * a class file such as detecting unknown attributes 	 * or trailing garbage at the end of a class file. 	 * However, Markus Dahm does not like the idea so this 	 * method is currently useless and therefore marked as 	 *<B>TODO</B>. 	 */
specifier|public
name|String
index|[]
name|getMessages
parameter_list|()
block|{
comment|// This method is only here to override the javadoc-comment.
return|return
name|super
operator|.
name|getMessages
argument_list|()
return|;
block|}
block|}
end_class

end_unit

