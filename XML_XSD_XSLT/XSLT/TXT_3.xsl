<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" indent="yes"/>

    <!-- Elaborado por mim e testado manualmente no http://xsltransform.net/ -->

    <xsl:template match="/">Chão de Fábrica<xsl:for-each select="child::*/*">
        <!-- Quando o elemento tem valores lá dentro, se não tiver, não escreve nada-->
        <!-- O Select converte o nome indicado no XML num nome com capitalização na primeira letra-->
        <xsl:if test="count(*) > 0"><xsl:text>&#xa;&#x9;</xsl:text><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),
            substring(local-name(), 2),
            ' '[not(last())]
        )"/> - <xsl:value-of select="count(*)"/>
        </xsl:if>
    </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>