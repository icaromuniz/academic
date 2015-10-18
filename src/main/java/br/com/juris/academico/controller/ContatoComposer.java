package br.com.juris.academico.controller;

import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import br.com.juris.academico.model.PessoaFisica;
import br.com.juris.academico.model.Turma;
import br.com.juris.academico.persistence.PessoaFisicaDao;
import br.com.juris.academico.persistence.TurmaDao;
import br.com.juris.academico.service.Util;

public class ContatoComposer extends BindComposer<Component> {

	private static final long serialVersionUID = -1760397848743809464L;
	
	TurmaDao turmaDao = Util.getDao(TurmaDao.class);
	
	Combobox filtroUnidade;
	Combobox filtroTurma;
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) throws Exception {
		
		// redireciona o usuário para a tela de login caso ele não esteja logado
		if( !Executions.getCurrent().getDesktop().getRequestPath().equals("/geral/login.zul") && 
				Executions.getCurrent().getSession().getAttribute("usuario") == null ){
			Executions.sendRedirect("/geral/login.zul");
		}
		
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		// executa os binds
		ConventionWires.wireFellows(getBinder().getView().getSpaceOwner(), this);

		super.doAfterCompose(comp);

		// atualiza o título da página
		comp.getPage().setTitle("Júris Acadêmico - " + ((Window)comp).getTitle());
		
		getBinder().notifyChange(this, "*");
	}
	
	public void limpaFiltro(){
		Executions.sendRedirect(Executions.getCurrent().getDesktop().getRequestPath());
	}
	
	public void emiteRelatorio(){
		
		PessoaFisicaDao pessoaFisicaDao = Util.getDao(PessoaFisicaDao.class);
		List<PessoaFisica> listaPessoaFisica = pessoaFisicaDao.findByFiltro(null, null, null);
		HashMap<String, Object> hashMap = new HashMap<>();
		
		try {
			Filedownload.save( JasperRunManager.runReportToPdf(
					Executions.getCurrent().getDesktop().getWebApp().getRealPath("/relatorio/contatos.jasper"),
					hashMap, new JRBeanCollectionDataSource(listaPessoaFisica)), null, "Contatos.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO Criar RelatorioComposer incluindo os metodos até aqui
	
	public void trataSelecaoFiltroUnidade(){
		((ListModelList<?>) filtroTurma.getModel()).clear();
		getBinder().notifyChange(this, "listaTurma");
	}

	public List<Turma> getListaTurma(){
		
		if (filtroUnidade.getSelectedItem() == null) {
			return null;
		}
		
		return turmaDao.findByFiltro(null, filtroUnidade.getSelectedItem() != null ? filtroUnidade.getSelectedItem().getLabel() : null, null);
	}
}
