package com.adobe.ac.pmd.parser;

import java.io.IOException;

import com.adobe.ac.pmd.parser.exceptions.TokenException;

public interface IAS3Parser
{
   IParserNode buildAst( final String filePath ) throws IOException,
                                                TokenException;

   IParserNode buildAst( final String filePath,
                         final String[] scriptBlockLines ) throws IOException,
                                                          TokenException;
}