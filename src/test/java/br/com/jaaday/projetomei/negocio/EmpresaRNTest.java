/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.negocio;

import br.com.jaaday.projetomei.modelo.Empresa;
import br.com.jaaday.projetomei.modelo.Empresario;
import br.com.jaaday.projetomei.modelo.Municipio;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sephi_000
 */
public class EmpresaRNTest {
    EmpresaRN empresaRN;
    MunicipioRN municipioRN;
    EmpresarioRN empresarioRN;
    
    public EmpresaRNTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        empresaRN = new EmpresaRN();
        municipioRN = new MunicipioRN();
        empresarioRN = new EmpresarioRN();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of inserir method, of class EmpresaRN.
     */
    @Test
    public void testInserir() {
        System.out.println("inserir");
        
        municipioRN.setMunicipio(new Municipio(2, "CAICÓ"));
        municipioRN.inserir();
        
        empresarioRN.setEmpresario(new Empresario(2, "06947064422", "jaaday melkran", "Maria Jose de Morais"));
        empresarioRN.inserir();
        
        empresaRN.getEmpresa().setAtivo(true);
        empresaRN.getEmpresa().setContador(false);
        empresaRN.getEmpresa().setCpfCnpj("06947064422");
        empresaRN.getEmpresa().setDdd("84");
        empresaRN.getEmpresa().setDescricaoObjeto("A empresa teste será inserida");
        empresaRN.getEmpresa().setEmail("jaaday.ufrn@gmail.com");
        empresaRN.getEmpresa().setNome("JAADAY MELKRAN DA SILVA");
        empresaRN.getEmpresa().setRamal("000");
        empresaRN.getEmpresa().setStatusSolicitacao("AGUARDANDO RESPOSTA");
        empresaRN.getEmpresa().setTelefone("998921339");
        empresaRN.getEmpresa().setMunicipioId(municipioRN.getMunicipio());
        empresaRN.getEmpresa().setEmpresarioId(empresarioRN.getEmpresario());
        empresaRN.inserir();
        
        Empresa result = empresaRN.buscar(empresaRN.getEmpresa().getId());
        Empresa expectResult = null;
        assertNotEquals(expectResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of deletar method, of class EmpresaRN.
     */
    @Test
    public void testDeletar() {
        System.out.println("deletar");
        Integer id = empresaRN.getEmpresa().getId();
        empresaRN.deletar(id);
        Empresa result = empresaRN.buscar(id);
        Empresa expectResult = null;
        assertEquals(expectResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of editar method, of class EmpresaRN.
     */
    @Test
    public void testEditar() {
        System.out.println("editar");
        empresaRN = new EmpresaRN();
        empresaRN.editar();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of buscar method, of class EmpresaRN.
     */
    @Test
    public void testBuscar() {
        System.out.println("buscar");
        Integer id = 1;
        empresaRN = new EmpresaRN();
        Empresa expResult = null;
        Empresa result = empresaRN.buscar(id);
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of buscarTodos method, of class EmpresaRN.
     */
    @Test
    public void testBuscarTodos() {
        System.out.println("buscarTodos");
        empresaRN = new EmpresaRN();
        List<Empresa> expResult = null;
        List<Empresa> result = empresaRN.buscarTodos();
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEmpresa method, of class EmpresaRN.
     */
    @Test
    public void testGetEmpresa() {
        System.out.println("getEmpresa");
        empresaRN = new EmpresaRN();
        Empresa expResult = null;
        Empresa result = empresaRN.getEmpresa();
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setEmpresa method, of class EmpresaRN.
     */
    @Test
    public void testSetEmpresa() {
        System.out.println("setEmpresa");
        Empresa empresa = null;
        empresaRN = new EmpresaRN();
        empresaRN.setEmpresa(empresa);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
