package com.fortes.thumb;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class GeradorDeThumbnailUtils {

    public static final String prefix = "Thumb_";
    public static final int qualidade = 85; // 85%
    
    public static final int LARGURA_DEFAULT = 144; // em pixels
    public static final int ALTURA_DEFAULT = 180; // em pixels
    
    private final static Log logger = LogFactory.getLog(GeradorDeThumbnailUtils.class);
    
    private int largura;
    private int altura;
    
    public GeradorDeThumbnailUtils() {
    	this(LARGURA_DEFAULT, ALTURA_DEFAULT);
    }
    
    public GeradorDeThumbnailUtils(int larguraEmPixels, int alturaEmPixels) {
		this.largura = larguraEmPixels;
		this.altura = alturaEmPixels;
	}

	/**
	 * Gera thumbnail (jpeg) da <code>imagemDeOrigem</code> no diretório temporário do
	 * OS e retorna <code>File</code> da imagem gerada.
	 */
	public File gera(String imagemDeOrigem) {
    	File thumb;
		try {
			thumb = File.createTempFile(prefix, ".jpg");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao tentar criar thumbnail temporário", e);
		}
    	gera(imagemDeOrigem, thumb.getAbsolutePath());
    	return thumb;
    }

	/**
	 * Classe responsável em a partir de uma imagem ela gera uma thumbnail
	 * (jpeg) dessa imagem.<br>
	 * Deve-se passar o nomeImagemOriginal juntamente com o caminho que a imagem
	 * se encontra. Da mesma forma deve ser feito com o nomeImagemThumb, com o
	 * nome do Thumb mais o caminho onde ele deve ser salvo.
	 */
    public void gera(String imagemDeOriginal, String imagemDeThumb) {
        try {
            
            // Carrega a imagem original
            Image imagem = carregaImagemOriginal(imagemDeOriginal);

            // TODO: melhorar esse código, torna-lo mais legivel
            // define a largura e altura do thumbnail
            int largura = this.largura;
            int altura = this.altura;
            double thumbRatio = (double) largura / (double) altura;
            int larguraDaImagemOriginal = imagem.getWidth(null);
            int alturaDaImagemOriginal = imagem.getHeight(null);
            double imageRatio = (double) larguraDaImagemOriginal / (double) alturaDaImagemOriginal;
            if (thumbRatio < imageRatio) {
                altura = (int) (largura / imageRatio);
            } else {
                largura = (int) (altura * imageRatio);
            }
            
            BufferedImage thumbImage = redimensionaImagemOriginalParaThumbnail(imagem, largura, altura);
            salvaThumbEmDisco(imagemDeThumb, thumbImage);
            
            logger.info("Thumbmail gerado em: " + imagemDeThumb);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar thumbnail: " + e.getMessage(), e);
        }
    }
    
    public String getContentType() {
    	return "image/jpeg";
    }

	/**
	 * Salva a nova imagem (thumbnail) em disco.
	 */
	private void salvaThumbEmDisco(String pathThumb, BufferedImage thumbImage) {
		
		BufferedOutputStream out = null;
		try {
			
			out = new BufferedOutputStream(new FileOutputStream(pathThumb));

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
			param.setQuality((float) qualidade / 100.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(thumbImage);
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar thumbnail em disco", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new RuntimeException("Erro ao fechar outputstream do thumbnail", e);
				}
			}
		}
	}

	/**
	 * Desenha a imagem original para o thumbnail e redimensiona para o novo
	 * tamanho.
	 */
	private BufferedImage redimensionaImagemOriginalParaThumbnail(Image imagem, int largura, int altura) {
		
		BufferedImage thumbImage = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(imagem, 0, 0, largura, altura, null);
		return thumbImage;
	}

	/**
	 * Carrega a imagem original.
	 */
	private Image carregaImagemOriginal(String nomeDaImagemOriginal) throws InterruptedException {
		Image imagem = Toolkit.getDefaultToolkit().getImage(nomeDaImagemOriginal);
		imagem.flush(); // evita cache da imagem
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(imagem, 0);
		mediaTracker.waitForID(0);
		return imagem;
	}

    /**
     * Método que retorna o caminho completo da imagem renomeado para o thumb.<br>
     * Ex: <br>
     * /uploads/eventos/imagemOriginal.jpg<br>
     * /uploads/eventos/<strong>Thumb_</strong>imagemOriginal.jpg<br>
     */
    public static String getThumbPath(String nomeDaImagemOriginal) {
        String nomeDaImagem = nomeDaImagemOriginal.substring(nomeDaImagemOriginal.lastIndexOf(File.separator) + 1);
        String caminhoDaImagemTemporaria = System.getProperty("java.io.tmpdir");
        return caminhoDaImagemTemporaria + prefix + nomeDaImagem;
    }
    
    public static void main(String[] args) throws IOException {
		
    	GeradorDeThumbnailUtils gerador = new GeradorDeThumbnailUtils();
    	
    	String imagemDeOrigem = "/Users/rponte/Development/imagens_fortes/rponte.jpg";
		String thumb = "/Users/rponte/Development/imagens_fortes/thumb_rponte.jpg";
		
//		gerador.gera(imagemDeOrigem, thumb);
		File img = gerador.gera(imagemDeOrigem);
    	System.out.println(img.getAbsolutePath());
//    	System.out.println(FileUtil.getMimeType(img));
	}
    
}
