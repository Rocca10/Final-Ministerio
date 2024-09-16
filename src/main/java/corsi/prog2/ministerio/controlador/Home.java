package corsi.prog2.ministerio.controlador;

import corsi.prog2.ministerio.modelo.Entidad;
import corsi.prog2.ministerio.modelo.RepositorioDeEntidades;
import corsi.prog2.ministerio.modelo.RepositorioDeSucursales;
import corsi.prog2.ministerio.modelo.RepositorioDeUsuarios;
import corsi.prog2.ministerio.modelo.Resumen;
import corsi.prog2.ministerio.modelo.Sucursal;
import corsi.prog2.ministerio.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class Home {

    @Autowired
    private RepositorioDeUsuarios ru;

    @Autowired
    private RepositorioDeSucursales rs;

    @Autowired
    private RepositorioDeEntidades re;

    private String login(Model model, Usuario usuario, Resumen resumen) {
        if (usuario != null) {
            model.addAttribute("resumen", resumen);
            model.addAttribute("usuario", usuario);
            model.addAttribute("usuarios", ru.findAll());
            model.addAttribute("sucursales", rs.findAll());
            model.addAttribute("entidades", re.findAll());
            return usuario.getRol().toLowerCase();
        }
        model.addAttribute("error", "No se encontró Código/Password");
        return "index";
    }

    @PostMapping("/login")
    public String home(Model model,
                       @RequestParam(name = "codigo", required = true) String codigo,
                       @RequestParam(name = "password", required = true) String password) {
        Usuario usuario = null;
        List<Usuario> match = ru.findByCodigoAndPassword(codigo, password);
        if (!match.isEmpty()) {
            usuario = match.get(0);
        }
        return login(model, usuario, null);
    }

    @GetMapping("/login")
    public String home(Model model) {
        return login(model, (Usuario) model.getAttribute("usuario"), (Resumen) model.getAttribute("resumen"));
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/resumen/{codigo}")
    public String resumen(final RedirectAttributes redirectAttributes,
                          @PathVariable(value = "codigo") String adminId) {
        Resumen r = null;
        redirectAttributes.addFlashAttribute("resumen", r);
        redirectAttributes.addFlashAttribute("usuario", ru.findById(adminId).get());
        return "redirect:/login#";
    }


    @PostMapping("/guardar-sucursal")
    public String guardarSucursal(@RequestParam(name = "codigo") String codigo,
                                  @RequestParam(name = "domicilio") String domicilio,
                                  @RequestParam(name = "cantEmpleados") String empleados,
                                  @RequestParam(name = "entidadId") Long entidadId,
                                  final RedirectAttributes redirectAttributes) {
        Sucursal sucursal = new Sucursal();
        sucursal.setCodigo(codigo);
        sucursal.setDomicilio(domicilio);
        sucursal.setCantidadEmpleados(empleados);
        Entidad entidad = re.findById(entidadId).orElseThrow(() -> new IllegalArgumentException("Entidad no encontrada"));
        sucursal.setEntidad(entidad);
        rs.save(sucursal);
        redirectAttributes.addFlashAttribute("usuario", ru.findById("adminId").orElse(null));
        return "redirect:/login#sucursales";
    }

    @PostMapping("/borrar-sucursal/{id}")
    public String borrarSucursal(@PathVariable Long id,
                                 final RedirectAttributes redirectAttributes) {
        rs.deleteById(id);
        redirectAttributes.addFlashAttribute("usuario", ru.findById("adminId").orElse(null));
        return "redirect:/login#sucursales";
    }
}
