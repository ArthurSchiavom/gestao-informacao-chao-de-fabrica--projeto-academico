<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="3.0">
    
<!--___________________________
    &#x9; = Tab
    &#x10; = New Line
____________________________-->
    
    <xsl:output method="text"/>
    
    <xsl:template match="/">{
    <xsl:for-each select="./*">"<xsl:value-of select="local-name()"/>": 
    {
    <xsl:apply-templates select="*"/></xsl:for-each>}
}
    </xsl:template>

    <xsl:template match="*">
        <xsl:text>&#x9;</xsl:text>"<xsl:value-of select="local-name()"/>": <xsl:call-template name="list"/>
    </xsl:template>
    
    <xsl:template name="list">
        {
        <xsl:call-template name="count"></xsl:call-template>
        <xsl:call-template name="ids"></xsl:call-template>
        }
    </xsl:template>
    
    <xsl:template name="count">    "numero" = "<xsl:value-of select="count(./*)"/>"</xsl:template>
    
    <xsl:template name="ids">
        <xsl:if test="count(./*/@*) > 0">
            "IDs": [<xsl:for-each select="./*">
                   <text></text>"<xsl:value-of select="attribute::*"/>"<xsl:if test="count(following-sibling::*) > 0">, </xsl:if>
                </xsl:for-each>]</xsl:if>
    </xsl:template>
    
</xsl:stylesheet>