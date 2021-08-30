package com.dxc.eproc.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueAdminServiceApplication.
 *
 * @author vkaranam7
 */
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableJms
@EnableTransactionManagement
@SpringBootApplication
public class CatalogueAdminServiceApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CatalogueAdminServiceApplication.class, args);
	}

}
