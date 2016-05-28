/*
 * http://quebrandoparadigmas.com/?p=751
 */
package sig.controller.application;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import sig.controller.application.ApplicationControlMB;
import sig.controller.application.LoginMB;

/**
 * The DefaultPhaseListener class is called when request is made to the server,
 * request are: load a page, pressing a button and the database is queried.
 *
 * @author nelson
 */

/*
 * ingresa a esta clase cada que hay un cambio de fase
 * (fase = solicitud al servidor)
 */
public class DefaultPhaseListener implements PhaseListener {

    ApplicationControlMB applicationControlMB;
    private LoginMB loginMB;
    //private InjuriesCountMB injuriesCountMB;

    /**
     * It is responsible for searching the list of sessions, the ID of the
     * current session, as well to allows determine if there is inactivity,
     * otherwise it redirects to the home page of SIGEODEP.
     *
     * @param event
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        ExternalContext ext = facesContext.getExternalContext();
        boolean continueProcces = true;
        //---------------------------------------------------------------------------
        //buscar ID session actual dentro de lista de sesiones en ApplicationControl
        //---------------------------------------------------------------------------
        try {
            applicationControlMB = (ApplicationControlMB) ext.getApplicationMap().get("applicationControlMB");
            loginMB = (LoginMB) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginMB}", LoginMB.class);
            //injuriesCountMB = (InjuriesCountMB) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{injuriesCountMB}", InjuriesCountMB.class);
            if (loginMB.isAutenticado()) {
                if (!applicationControlMB.findIdSession(loginMB.getIdSession())) {//System.out.println("salida del programa por que se inicio nueva sesion en otro equipo");
                    continueProcces = false;
                    loginMB.logout();
                }
            }
        } catch (Exception e) {
        }

        //injuriesCountMB.reset();
        //---------------------------------------------------------------------------
        //--se determina si hubo inactividad o no se a iniciado session
        //---------------------------------------------------------------------------        
        if (continueProcces) {
            String ctxPath = ((ServletContext) ext.getContext()).getContextPath();
            String currentPage = facesContext.getViewRoot().getViewId();
            boolean isLoginPage;
            isLoginPage = (currentPage.lastIndexOf("index.xhtml") > -1);//determinar si es index para usuario del sistema
           

            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

            if (!isLoginPage) {
                if (session == null) {
                    try {//System.out.println("salida del programa por que sesion es null" + ctxPath);

                        ext.redirect(ctxPath + "/?v=timeout");//System.out.println("enviado a: " + ctxPath + "/index.html?v=timeout");

                    } catch (Throwable t) {//System.out.println("Fallo al expirar sesion " + t.toString());
                        throw new FacesException("Session timed out", t);
                    }
                } else {
                    Object currentUser = session.getAttribute("username");
                    if (!isLoginPage && (currentUser == null || currentUser == "")) {
                        try {//System.out.println("salida del programa por que no hay usuario registrado" + ctxPath);

                            ext.redirect(ctxPath + "/?v=nosession");

                        } catch (Exception e) {//System.out.println("Fallo al expirar sesion " + e.toString());
                            throw new FacesException("Session no login", e);
                        }
                    }
                }
            }
        }
    }

    /**
     * is called this function when the request ends of be processed by the
     * server.
     *
     * @param event
     */
    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}