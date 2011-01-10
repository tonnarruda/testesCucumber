package com.fortes.f2rh.test;

import com.fortes.f2rh.Curriculo;
import com.fortes.f2rh.F2rhDownloadFacade;
import com.fortes.model.type.File;

public class MockF2rhDownloadFacade implements F2rhDownloadFacade {

	public File download(Curriculo curriculo) {
		File foto = new File();
		foto.setName(curriculo.getFoto_file_name());
		foto.setContentType("image/jpeg");
		foto.setBytes(new byte[1000]);
		foto.setSize(1000L);
		return foto;
	}

}
