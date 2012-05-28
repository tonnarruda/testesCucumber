package com.fortes.test.thumb;

import java.io.File;

import com.fortes.thumb.GeradorDeThumbnailUtils;

import junit.framework.TestCase;

public class GeradorDeThumbnailUtilsTest extends TestCase {

	GeradorDeThumbnailUtils gerador;
	File imagemOriginal;
	
	public void setUp() {
		gerador = new GeradorDeThumbnailUtils();
		imagemOriginal = new File(getCaminhoDaImagemOriginal());
	}
	
	public void testDeveriaGerarThumbnail() {
		
		File thumb = gerador.gera(getCaminhoDaImagemOriginal());
		
		comPrefixo(thumb);	
		comSufixo(thumb);	
		comTamanhoInferiorAImagemOriginal(thumb);
	}

	private void comTamanhoInferiorAImagemOriginal(File thumb) {
		assertTrue("tamanho", thumb.length() < imagemOriginal.length());
	}

	private void comSufixo(File thumb) {
		assertTrue("sufixo", thumb.getName().endsWith(".jpg"));
	}

	private void comPrefixo(File thumb) {
		assertTrue("prefixo", thumb.getName().startsWith("Thumb_"));
	}
	
	private String getCaminhoDaImagemOriginal() {
		return this.getClass().getResource("imagem_original.png").getFile().replace("\\", "/").replace("%20", " ");
	}
	
}
