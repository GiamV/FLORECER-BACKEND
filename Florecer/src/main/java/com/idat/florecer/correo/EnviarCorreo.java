package com.idat.florecer.correo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.idat.florecer.entity.CabeceraVenta;
import com.idat.florecer.entity.DetalleVenta;
import com.idat.florecer.service.ICabeceraVentaService;
import com.idat.florecer.service.IDetalleVentaService;
import com.idat.florecer.service.IUsuarioService;
import com.idat.florecer.serviceR.UsuarioServiceIm;

@CrossOrigin(origins = { "http://localhost/4200" })
@RestController
@RequestMapping("/correo")
public class EnviarCorreo {

	@Autowired
	private JavaMailSender mail;
	@Autowired
	@Qualifier("templateEngine")
	private TemplateEngine templateEngine;
	@Autowired
	private IDetalleVentaService detalleService;

	@Autowired
	private ICabeceraVentaService cabeceraService;

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/enviar/{idUsuario}/{correo}")
	public ResponseEntity<?> enviar_correo(@PathVariable Long idUsuario, @PathVariable String correo) {
		SimpleMailMessage email = new SimpleMailMessage();

		List<DetalleVenta> listaDetalle = detalleService.findAll();
		ArrayList<DetalleVenta> ListaCarrito = new ArrayList<DetalleVenta>();

		for (int i = 0; i < listaDetalle.size(); i++) {

			// cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera());
			if ((cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera())).getTipoCabecera()
					.equals("Carrito")
					&& (usuarioService.findById(cabeceraService
							.findById(listaDetalle.get(i).getCabecera().getIdCabecera()).getUsuario().getIdUsuario())
							.getIdUsuario().equals(idUsuario))) {
				ListaCarrito.add(listaDetalle.get(i));
			}
		}

		email.setTo(correo);
		email.setFrom("giamargvilla@gmail.com");
		email.setSubject("FACTURACIÓN FLORECER");
		String Mensaje = "Gracias por su compra\nEstos son los detalles de su compra:\n";
		Double total = 0.0;

		for (int i = 0; i < ListaCarrito.size(); i++) {
			Mensaje = Mensaje + "\n" + ListaCarrito.get(i).getProducto().getProducto() + " | CANTIDAD: "
					+ ListaCarrito.get(i).getCantidad() + " | PRECIO: " + ListaCarrito.get(i).getPrecio()
					+ " | SUBTOTAL: " + ListaCarrito.get(i).getCantidad() * ListaCarrito.get(i).getPrecio();
			total = total + ListaCarrito.get(i).getCantidad() * ListaCarrito.get(i).getPrecio();
		}
		Mensaje = Mensaje + "\nTOTAL PAGADO: " + (total + (total * 0.18))
				+ "\nGracias por la preferencia!\nSu pedido estara Listo Pronto";
		email.setText(Mensaje);

		mail.send(email);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/{correo}") public String recuperar(@PathVariable String correo)
	 * { SimpleMailMessage email = new SimpleMailMessage(); Usuario
	 * usuarioActual=userService.recuperacino(correo);
	 * 
	 * if(usuarioActual==null) { return "1"; }else { int min = 100000; int max =
	 * 999999; Random random = new Random(); int randomNumber = random.nextInt(max -
	 * min + 1) + min;
	 * 
	 * email.setTo(correo); email.setFrom("ln72885231@idat.edu.pe");
	 * email.setSubject("RECUPERACION DE CONTRASEÑA"); email.
	 * setText("Se Solicito la recuperación de contraseña\nDigite este codigo:"
	 * +randomNumber+""); mail.send(email); return randomNumber+"" ; }
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	@GetMapping("/plantilla/{idUsuario}/{correo}/{direc}/{distrito}/{nrodom}/{ref}")
	@ResponseBody
	public ResponseEntity<?> recuperar(@PathVariable Long idUsuario, @PathVariable String correo,@PathVariable String direc,
			@PathVariable String distrito,@PathVariable String nrodom,@PathVariable String ref) throws MessagingException {
		
		
		
		MimeMessage message = mail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setSubject("Información importante");
		helper.setTo(correo);
		helper.setFrom("ln72885231@idat.pe");

		List<DetalleVenta> listaDetalle = detalleService.findAll();
		ArrayList<DetalleVenta> ListaCarrito = new ArrayList<DetalleVenta>();
		Double total = 0.0;

		for (int i = 0; i < listaDetalle.size(); i++) {

			// cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera());
			if ((cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera())).getTipoCabecera()
					.equals("Carrito")
					&& (usuarioService.findById(cabeceraService
							.findById(listaDetalle.get(i).getCabecera().getIdCabecera()).getUsuario().getIdUsuario())
							.getIdUsuario().equals(idUsuario))) {
				ListaCarrito.add(listaDetalle.get(i));
				total = total + listaDetalle.get(i).getCantidad() * listaDetalle.get(i).getPrecio();
				
			}
		}
		Context context = new Context();
		context.setVariable("nombre", usuarioService.findById(idUsuario).getNombre() + " "
				+ usuarioService.findById(idUsuario).getApellido());
		context.setVariable("detalles", ListaCarrito);
		DecimalFormat formato = new DecimalFormat("#.##");
		context.setVariable("igv", formato.format((total * 0.18)));
		context.setVariable("bruto", total);
		context.setVariable("neto", formato.format(total+(total * 0.18)) );
		context.setVariable("direccion", direc);
		context.setVariable("distrito", distrito);
		context.setVariable("nrodom", nrodom);
		context.setVariable("ref", ref);

		String plantilla = "prueba";
		String rutaPlantilla = "classpath:/templates/" + plantilla + ".html";
		System.out.println(ref);

		String content = templateEngine.process(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'\">\r\n"
				+ " <head>\r\n"
				+ "  <meta charset=\"UTF-8\">\r\n"
				+ "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\r\n"
				+ "  <meta name=\"x-apple-disable-message-reformatting\">\r\n"
				+ "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "  <meta content=\"telephone=no\" name=\"format-detection\">\r\n"
				+ "  <title>Nueva plantilla de correo electrC3B3nico 2023-04-13</title><!--[if (mso 16)]>\r\n"
				+ "    <style type=\"text/css\">\r\n"
				+ "    a {text-decoration: none;}\r\n"
				+ "    </style>\r\n"
				+ "    <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\r\n"
				+ "<xml>\r\n"
				+ "    <o:OfficeDocumentSettings>\r\n"
				+ "    <o:AllowPNG></o:AllowPNG>\r\n"
				+ "    <o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
				+ "    </o:OfficeDocumentSettings>\r\n"
				+ "</xml>\r\n"
				+ "<![endif]-->\r\n"
				+ "  <style type=\"text/css\">\r\n"
				+ "#outlook a {\r\n"
				+ "	padding:0;\r\n"
				+ "}\r\n"
				+ ".es-button {\r\n"
				+ "	mso-style-priority:100!important;\r\n"
				+ "	text-decoration:none!important;\r\n"
				+ "}\r\n"
				+ "a[x-apple-data-detectors] {\r\n"
				+ "	color:inherit!important;\r\n"
				+ "	text-decoration:none!important;\r\n"
				+ "	font-size:inherit!important;\r\n"
				+ "	font-family:inherit!important;\r\n"
				+ "	font-weight:inherit!important;\r\n"
				+ "	line-height:inherit!important;\r\n"
				+ "}\r\n"
				+ ".es-desk-hidden {\r\n"
				+ "	display:none;\r\n"
				+ "	float:left;\r\n"
				+ "	overflow:hidden;\r\n"
				+ "	width:0;\r\n"
				+ "	max-height:0;\r\n"
				+ "	line-height:0;\r\n"
				+ "	mso-hide:all;\r\n"
				+ "}\r\n"
				+ "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120% } h1 { font-size:30px!important; text-align:center } h2 { font-size:26px!important; text-align:center } h3 { font-size:20px!important; text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:12px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:12px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:20px!important; display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0!important } .es-m-p0r { padding-right:0!important } .es-m-p0l { padding-left:0!important } .es-m-p0t { padding-top:0!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-m-p5 { padding:5px!important } .es-m-p5t { padding-top:5px!important } .es-m-p5b { padding-bottom:5px!important } .es-m-p5r { padding-right:5px!important } .es-m-p5l { padding-left:5px!important } .es-m-p10 { padding:10px!important } .es-m-p10t { padding-top:10px!important } .es-m-p10b { padding-bottom:10px!important } .es-m-p10r { padding-right:10px!important } .es-m-p10l { padding-left:10px!important } .es-m-p15 { padding:15px!important } .es-m-p15t { padding-top:15px!important } .es-m-p15b { padding-bottom:15px!important } .es-m-p15r { padding-right:15px!important } .es-m-p15l { padding-left:15px!important } .es-m-p20 { padding:20px!important } .es-m-p20t { padding-top:20px!important } .es-m-p20r { padding-right:20px!important } .es-m-p20l { padding-left:20px!important } .es-m-p25 { padding:25px!important } .es-m-p25t { padding-top:25px!important } .es-m-p25b { padding-bottom:25px!important } .es-m-p25r { padding-right:25px!important } .es-m-p25l { padding-left:25px!important } .es-m-p30 { padding:30px!important } .es-m-p30t { padding-top:30px!important } .es-m-p30b { padding-bottom:30px!important } .es-m-p30r { padding-right:30px!important } .es-m-p30l { padding-left:30px!important } .es-m-p35 { padding:35px!important } .es-m-p35t { padding-top:35px!important } .es-m-p35b { padding-bottom:35px!important } .es-m-p35r { padding-right:35px!important } .es-m-p35l { padding-left:35px!important } .es-m-p40 { padding:40px!important } .es-m-p40t { padding-top:40px!important } .es-m-p40b { padding-bottom:40px!important } .es-m-p40r { padding-right:40px!important } .es-m-p40l { padding-left:40px!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }\r\n"
				+ "</style>\r\n"
				+ " </head>\r\n"
				+ " <body style=\"width:100%;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n"
				+ "  <div class=\"es-wrapper-color\" style=\"background-color:#FFFFFF\"><!--[if gte mso 9]>\r\n"
				+ "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n"
				+ "				<v:fill type=\"tile\" color=\"#ffffff\"></v:fill>\r\n"
				+ "			</v:background>\r\n"
				+ "		<![endif]-->\r\n"
				+ "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#FFFFFF\">\r\n"
				+ "     <tr>\r\n"
				+ "      <td valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:#FFAF87;background-repeat:repeat;background-position:center top\">\r\n"
				+ "         <tr>\r\n"
				+ "          <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding:0;Margin:0;background-color:#ffffff\">\r\n"
				+ "           <table bgcolor=\"#ffffff\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\">\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" style=\"Margin:0;padding-top:15px;padding-bottom:15px;padding-left:20px;padding-right:20px\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"http://localhost:4200/index\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img src=\"https://i.ibb.co/YpWB7kX/image23.png\" alt=\"Logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"85\" title=\"Logo\"></a></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "           </table></td>\r\n"
				+ "         </tr>\r\n"
				+ "       </table>\r\n"
				+ "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
				+ "         <tr>\r\n"
				+ "          <td align=\"center\" bgcolor=\"#f4cccc\" style=\"padding:0;Margin:0;background-color:#f4cccc\">\r\n"
				+ "           <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\r\n"
				+ "             <tr class=\"es-mobile-hidden\">\r\n"
				+ "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" style=\"padding:0;Margin:0\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"http://localhost:4200/cliente\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#333333;font-size:14px\"><img class=\"adapt-img\" src=\"https://www.alphadventure.com/wp-content/uploads/2014/09/OY_0424logo-e1418987870875.jpg\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"600\"></a></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-bottom:20px;padding-left:20px;padding-right:20px;padding-top:30px;background-color:#ffffff\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:30px;font-style:normal;font-weight:bold;color:#377771\">Tus Pedido&nbsp;esta&nbsp;en camino!!</h1></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" class=\"es-m-p20l\" style=\"padding:0;Margin:0;padding-left:10px;padding-top:20px;padding-bottom:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#377771;background:#ffd966;border-width:2px;display:inline-block;border-radius:30px;width:auto;mso-border-alt:10px\"><a href=\"http://localhost:4200/cliente\" class=\"es-button es-button-1660738825849\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#ffffff;font-size:16px;padding:5px 10px 5px 30px;display:inline-block;background:#ffd966;border-radius:30px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:19px;width:auto;text-align:center;border-color:#ffd966\">Ver Orden <!--[if !mso]><!-- --><img src=\"https://muxsnf.stripocdn.email/content/guids/CABINET_4ef70d5e55777b4a8c94bb922dbcbc58/images/95861625570219511.png\" alt=\"icon\" width=\"25\" align=\"absmiddle\" style=\"display:inline-block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;vertical-align:middle;margin-left:10px\"><!--<![endif]--></a></span></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding:20px;Margin:0;background-color:#ffffff\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:10px\"><h2 style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">Pedido de</h2></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0\">\r\n"
				+ "                       <table border=\"0\" width=\"50%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;display:inline-table;width:50% !important\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td style=\"padding:0;Margin:0;border-bottom:5px solid #ff8e72;background:none;height:1px;width:100%;margin:0px\"></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\"><b>Tiendas Florecer</b></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\">Florecer.com</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\">Lima, Perú</p></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:10px\"><h2 style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">Entregado a<br></h2></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0\">\r\n"
				+ "                       <table border=\"0\" width=\"50%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;display:inline-table;width:50% !important\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td style=\"padding:0;Margin:0;border-bottom:5px solid #ff8e72;background:none;height:1px;width:100%;margin:0px\"></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:10px\"><p id=\"usuario\" style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\"><b><span th:text=\"${nombre}\"></span></b></p><p id=\"direccion\" style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\"><span th:text=\"${direccion}\"></span> <span th:text=\"${nrodom}\"></span>, <span th:text=\"${distrito}\"></span>, Lima</p></p><p id=\"distrito\" style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\"><span th:text=\"${ref}\"></span></p></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td></tr></table><![endif]--></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;background-color:#ffffff\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" align=\"left\" class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"es-m-p20b\" align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:10px\"><h2 style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">Detalles del pedido<br></h2></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0\">\r\n"
				+ "                       <table border=\"0\" width=\"50%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;display:inline-table;width:50% !important\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td style=\"padding:0;Margin:0;border-bottom:5px solid #ff8e72;background:none;height:1px;width:100%;margin:0px\"></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                 <tr class=\"es-mobile-hidden\">\r\n"
				+ "                  <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td></tr></table><![endif]--></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr th:each=\"det : ${detalles}\">\r\n"
				+ "					<td  class=\"esdev-adapt-off\" align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-bottom:5px;padding-top:20px;padding-left:20px;padding-right:20px;background-color:#ffffff\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"esdev-mso-table\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:560px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td class=\"es-m-p0r\" align=\"center\" style=\"padding:0;Margin:0;width:30px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"left\" style=\"padding:0;Margin:0\"><p id=\"idPedido\" style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\" th:text=\"${det.cantidad}\"></p></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                  <td style=\"padding:0;Margin:0;width:20px\"></td>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;width:30px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\"><br></p></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                  <td style=\"padding:0;Margin:0;width:20px\"></td>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;width:315px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"left\" style=\"padding:0;Margin:0\"><p class=\"p_name\" style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\" th:text=\"${det.producto.producto}\"></p></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                  <td style=\"padding:0;Margin:0;width:20px\"></td>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" style=\"padding:0;Margin:0;width:125px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"right\" style=\"padding:0;Margin:0\"><p class=\"p_price\" style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#333333;font-size:14px\"> s/. <span th:text=\"${det.precio*det.cantidad}\"></span></p></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;background-color:#ffffff\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px;font-size:0\">\r\n"
				+ "                       <table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td style=\"padding:0;Margin:0;border-bottom:1px solid #cccccc;background:none;height:1px;width:100%;margin:0px\"></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td class=\"esdev-adapt-off\" align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-top:5px;padding-left:20px;padding-right:20px;padding-bottom:40px;background-color:#ffffff\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"esdev-mso-table\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:560px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td class=\"es-m-p0r\" align=\"center\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0\"><h2 style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">Igv</h2></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                  <td style=\"padding:0;Margin:0;width:20px\"></td>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"right\" class=\"es-m-txt-r\" style=\"padding:0;Margin:0\"><h2 id=\"igv\" style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">s/. <span th:text=\"${igv}\"></span></h2></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td class=\"esdev-adapt-off\" align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-top:5px;padding-left:20px;padding-right:20px;padding-bottom:40px;background-color:#ffffff\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"esdev-mso-table\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:560px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td class=\"es-m-p0r\" align=\"center\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0\"><h2 style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">Bruto</h2></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                  <td style=\"padding:0;Margin:0;width:20px\"></td>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"right\" class=\"es-m-txt-r\" style=\"padding:0;Margin:0\"><h2 id=\"bruto\" style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">s/. <span th:text=\"${bruto}\"></span></h2></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td class=\"esdev-adapt-off\" align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-top:5px;padding-left:20px;padding-right:20px;padding-bottom:40px;background-color:#ffffff\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"esdev-mso-table\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:560px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td class=\"es-m-p0r\" align=\"center\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0\"><h2 style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">Costo Total</h2></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                  <td style=\"padding:0;Margin:0;width:20px\"></td>\r\n"
				+ "                  <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"right\" class=\"es-m-txt-r\" style=\"padding:0;Margin:0\"><h2 id=\"total\" style=\"Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:24px;font-style:normal;font-weight:bold;color:#377771\">s/. <span th:text=\"${neto}\"></span></h2></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr class=\"es-mobile-hidden\">\r\n"
				+ "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "           </table></td>\r\n"
				+ "         </tr>\r\n"
				+ "       </table>\r\n"
				+ "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
				+ "         <tr>\r\n"
				+ "          <td align=\"center\" style=\"padding:0;Margin:0;background-color:#fbe6a3\" bgcolor=\"#FBE6A3\">\r\n"
				+ "           <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\r\n"
				+ "             <tr class=\"es-mobile-hidden\">\r\n"
				+ "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-top:30px;background-color:#ffffff\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:30px;font-style:normal;font-weight:bold;color:#377771\">Productos Destacados</h1></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" bgcolor=\"#ffffff\" style=\"Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;padding-bottom:30px;background-color:#ffffff\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:190px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:180px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><img src=\"https://hmperu.vtexassets.com/arquivos/ids/2736826-483-725/Cartera-en-tejido-de-jacquard---Black-Patterned---H-M-PE.jpg?v=637883777193070000\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" class=\"p_image\" width=\"152\"></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"Margin:0;padding-bottom:5px;padding-top:10px;padding-left:10px;padding-right:10px\"><h3 style=\"Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;font-style:normal;font-weight:bold;color:#377771\">Cartera Jackuard</h3></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" class=\"es-m-p20l\" style=\"padding:0;Margin:0;padding-left:10px;padding-bottom:15px;padding-top:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#377771;background:#f4cccc;border-width:2px;display:inline-block;border-radius:30px;width:auto;mso-border-alt:10px\"><a href=\"http://localhost:4200/index\" class=\"es-button es-button-1660739501049\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#ffffff;font-size:16px;padding:5px 10px 5px 30px;display:inline-block;background:#f4cccc;border-radius:30px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:19px;width:auto;text-align:center;border-color:#f4cccc\">Lo quiero <!--[if !mso]><!-- --><img src=\"https://muxsnf.stripocdn.email/content/guids/CABINET_4ef70d5e55777b4a8c94bb922dbcbc58/images/95861625570219511.png\" alt=\"icon\" width=\"25\" align=\"absmiddle\" style=\"display:inline-block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;vertical-align:middle;margin-left:10px\"><!--<![endif]--></a></span></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                  <td class=\"es-hidden\" style=\"padding:0;Margin:0;width:10px\"></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td><td style=\"width:180px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;Margin:0;width:180px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#333333;font-size:14px\"><img src=\"https://hmperu.vtexassets.com/arquivos/ids/2930447-483-725/Jean-acampanado-sin-cierre---Azul-denim-Gato---H-M-PE.jpg?v=637964439442770000\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" class=\"p_image\" width=\"152\"></a></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"Margin:0;padding-bottom:5px;padding-top:10px;padding-left:10px;padding-right:10px\"><h3 class=\"p_name\" style=\"Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;font-style:normal;font-weight:bold;color:#377771\">Jean Acampanado</h3></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" class=\"es-m-p20l\" style=\"padding:0;Margin:0;padding-left:10px;padding-bottom:15px;padding-top:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#377771;background:#f4cccc;border-width:2px;display:inline-block;border-radius:30px;width:auto;mso-border-alt:10px\"><a href=\"http://localhost:4200/index\" class=\"es-button es-button-1660739515136\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#ffffff;font-size:16px;padding:5px 10px 5px 30px;display:inline-block;background:#f4cccc;border-radius:30px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:19px;width:auto;text-align:center;border-color:#f4cccc\">Lo quiero <!--[if !mso]><!-- --><img src=\"https://muxsnf.stripocdn.email/content/guids/CABINET_4ef70d5e55777b4a8c94bb922dbcbc58/images/95861625570219511.png\" alt=\"icon\" width=\"25\" align=\"absmiddle\" style=\"display:inline-block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;vertical-align:middle;margin-left:10px\"><!--<![endif]--></a></span></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td><td style=\"width:10px\"></td><td style=\"width:180px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" style=\"padding:0;Margin:0;width:180px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#333333;font-size:14px\"><img src=\"https://hmperu.vtexassets.com/arquivos/ids/3373428-483-725/Sobrecamisa-Regular-Fit---Negro---H-M-PE.jpg?v=638110148456970000\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" class=\"p_image\" width=\"151\"></a></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"Margin:0;padding-bottom:5px;padding-top:10px;padding-left:10px;padding-right:10px\"><h3 style=\"Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:18px;font-style:normal;font-weight:bold;color:#377771\">Camisa Sobre<br></h3></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" class=\"es-m-p20l\" style=\"padding:0;Margin:0;padding-left:10px;padding-bottom:15px;padding-top:20px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#377771;background:#f4cccc;border-width:2px;display:inline-block;border-radius:30px;width:auto;mso-border-alt:10px\"><a href=\"http://localhost:4200/index\" class=\"es-button es-button-1660739519073\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#ffffff;font-size:16px;padding:5px 10px 5px 30px;display:inline-block;background:#f4cccc;border-radius:30px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:19px;width:auto;text-align:center;border-color:#f4cccc\">Lo quiero <!--[if !mso]><!-- --><img src=\"https://muxsnf.stripocdn.email/content/guids/CABINET_4ef70d5e55777b4a8c94bb922dbcbc58/images/95861625570219511.png\" alt=\"icon\" width=\"25\" align=\"absmiddle\" style=\"display:inline-block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;vertical-align:middle;margin-left:10px\"><!--<![endif]--></a></span></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td></tr></table><![endif]--></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr class=\"es-mobile-hidden\">\r\n"
				+ "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" height=\"40\" style=\"padding:0;Margin:0\"></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "           </table></td>\r\n"
				+ "         </tr>\r\n"
				+ "       </table>\r\n"
				+ "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
				+ "         <tr>\r\n"
				+ "          <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding:0;Margin:0;background-color:#ffffff\">\r\n"
				+ "           <table class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" bgcolor=\"#ffffff\">\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:20px;font-size:0px\"><a target=\"_blank\" href=\"http://localhost:4200/index\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img src=\"https://i.ibb.co/YpWB7kX/image23.png\" alt=\"Logo\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"98\" title=\"Logo\"></a></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"Margin:0;padding-top:5px;padding-bottom:5px;padding-left:20px;padding-right:20px;font-size:0\">\r\n"
				+ "                       <table border=\"0\" width=\"95%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td style=\"padding:0;Margin:0;border-bottom:2px solid #bb2cad;background:none;height:1px;width:100%;margin:0px\"></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td style=\"padding:0;Margin:0\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"es-menu\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr class=\"links\">\r\n"
				+ "                          <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0\" id=\"esd-menu-id-0\"><a target=\"_blank\" href=\"http://localhost:4200/cliente\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';color:#377771;font-size:14px;font-weight:normal\">CUENTA</a></td>\r\n"
				+ "                          <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0\" id=\"esd-menu-id-1\"><a target=\"_blank\" href=\"http://localhost:4200/cliente\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';color:#377771;font-size:14px;font-weight:normal\">TERMINOS DE USO</a></td>\r\n"
				+ "                          <td align=\"center\" valign=\"top\" width=\"33.33%\" style=\"Margin:0;padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;border:0\" id=\"esd-menu-id-2\"><a target=\"_blank\" href=\"http://localhost:4200/cliente\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;display:block;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';color:#377771;font-size:14px;font-weight:normal\">PRIVACIDAD</a></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" style=\"Margin:0;padding-top:5px;padding-bottom:5px;padding-left:20px;padding-right:20px;font-size:0\">\r\n"
				+ "                       <table border=\"0\" width=\"95%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td style=\"padding:0;Margin:0;border-bottom:2px solid #811376;background:none;height:1px;width:100%;margin:0px\"></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" \r\n"
				+ "                        cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"left\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#377771;font-size:14px\">No hiciste esta compra?&nbsp;<br><a target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#377771;font-size:14px\">Cambiar de contraseña</a></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';line-height:21px;color:#377771;font-size:14px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#377771;font-size:14px\">Verlo en el navegador</a></p></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"right\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0;font-size:0px\">\r\n"
				+ "                       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-table-not-adapt es-social\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                         <tr>\r\n"
				+ "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:20px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img title=\"Facebook\" src=\"https://muxsnf.stripocdn.email/content/assets/img/social-icons/logo-black/facebook-logo-black.png\" alt=\"Fb\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
				+ "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:20px\"><a target=\"_blank\" href=\"https://viewstripo.email.\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img title=\"Twitter\" src=\"https://muxsnf.stripocdn.email/content/assets/img/social-icons/logo-black/twitter-logo-black.png\" alt=\"Tw\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
				+ "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:20px\"><a target=\"_blank\" href=\"https://viewstripo.email.\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img title=\"Instagram\" src=\"https://muxsnf.stripocdn.email/content/assets/img/social-icons/logo-black/instagram-logo-black.png\" alt=\"Inst\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
				+ "                          <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0\"><a target=\"_blank\" href=\"https://www.youtube.com/@TheSnove\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:14px\"><img title=\"Youtube\" src=\"https://muxsnf.stripocdn.email/content/assets/img/social-icons/logo-black/youtube-logo-black.png\" alt=\"Yt\" width=\"24\" height=\"24\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
				+ "                         </tr>\r\n"
				+ "                       </table></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table><!--[if mso]></td></tr></table><![endif]--></td>\r\n"
				+ "             </tr>\r\n"
				+ "           </table></td>\r\n"
				+ "         </tr>\r\n"
				+ "       </table>\r\n"
				+ "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
				+ "         <tr>\r\n"
				+ "          <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding:0;Margin:0;background-color:#ffffff\">\r\n"
				+ "           <table class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\r\n"
				+ "             <tr>\r\n"
				+ "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\">\r\n"
				+ "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                 <tr>\r\n"
				+ "                  <td align=\"left\" style=\"padding:0;Margin:0;width:560px\">\r\n"
				+ "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
				+ "                     <tr>\r\n"
				+ "                      <td align=\"center\" class=\"es-infoblock made_with\" style=\"padding:0;Margin:0;line-height:120%;font-size:0;color:#CCCCCC\"><a target=\"_blank\" href=\"https://viewstripo.email/?utm_source=templates&utm_medium=email&utm_campaign=organic&utm_content=your_food_is_coming\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\"><img src=\"https://muxsnf.stripocdn.email/content/guids/CABINET_09023af45624943febfa123c229a060b/images/7911561025989373.png\" alt width=\"125\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
				+ "                     </tr>\r\n"
				+ "                   </table></td>\r\n"
				+ "                 </tr>\r\n"
				+ "               </table></td>\r\n"
				+ "             </tr>\r\n"
				+ "           </table></td>\r\n"
				+ "         </tr>\r\n"
				+ "       </table></td>\r\n"
				+ "     </tr>\r\n"
				+ "   </table>\r\n"
				+ "  </div>\r\n"
				+ " </body>\r\n"
				+ "</html>",
				context);
		System.out.println(ListaCarrito.get(0).getCantidad());
		helper.setText(content, true);

		mail.send(message);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}