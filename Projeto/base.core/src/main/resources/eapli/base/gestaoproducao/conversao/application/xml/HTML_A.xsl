<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <!-- Elaborado por mim e testado manualmente no http://xsltransform.net/ -->

    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;&#xa;</xsl:text>
        <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1"/>
                <link rel="stylesheet" href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"/>
                <!-- de https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css -->
                <script src="https://code.jquery.com/jquery-1.11.3.min.js"/>
                <!-- de https://code.jquery.com/jquery-1.11.3.min.js -->
                <script src="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"/>
                <!-- de https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js -->
            </head>
            <body>
                <h1>Chão de Fábrica</h1>
                <xsl:for-each select="child::*/*">
                    <!-- Quando o elemento tem valores lá dentro, se não tiver, não escreve nada-->
                    <!-- O Select converte o nome indicado no XML num nome com capitalização na primeira letra-->
                    <xsl:if test="count(*) > 0"><h2><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),
                    substring(local-name(), 2),
                    ' '[not(last())]
                )"/></h2>
                        <!-- Vai buscar um elemento da lista xml individual -->
                        <xsl:for-each select="child::*"><xsl:text>&#x9;</xsl:text><h3><!-- Verifica se tem atributo e se tiver escreve no titulo do elemento -->
                            <xsl:choose>
                                <xsl:when test="count(attribute::*)=1"><xsl:value-of select="attribute::*"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),substring(local-name(), 2),' '[not(last())])"/></xsl:otherwise>
                            </xsl:choose></h3><ul><xsl:apply-templates select="*" mode="Elemento"/></ul><!-- Chama o template que trata dos value objects de cada elemento de dominio-->
                        </xsl:for-each>
                    </xsl:if>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="*" mode="Elemento">
        <xsl:text>&#x9;</xsl:text>
        <!-- Verifica se o elemento tem mais subelementos, se tiver chama outra fez este template de forma recursiva até chegar ao ultimo-->
        <xsl:choose>
            <xsl:when test="count(*)>0"><li><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),substring(local-name(), 2),' '[not(last())])"/></li>
                <ul><xsl:apply-templates select="*" mode="Elemento"/></ul></xsl:when>
            <xsl:otherwise><li><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),substring(local-name(), 2),' '[not(last())])"/> - <xsl:value-of select="."/></li></xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>