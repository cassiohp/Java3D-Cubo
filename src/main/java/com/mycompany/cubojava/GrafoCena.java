/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cubojava;

import com.sun.j3d.utils.geometry.ColorCube;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Csw2
 */
public class GrafoCena extends JFrame implements KeyListener{
    
    /*Universo Virtual*/
    private VirtualUniverse universo;

    /*Ponto de referência no mundo virtual - localização dos objetos*/
    private Locale locale;

    /*Objeto que faz a ligação ente o universo virtual e a janela*/
    private Canvas3D canvas3D;

    /*Objetos que fazem parte do subgrafo de contexto*/
    private ColorCube cubo;

    /*Variável que controla o estilo da tela: true (modo FullScreen), false (modo janela)*/
    boolean screenMode = false;

    public GrafoCena(){
        /*Utilização de um objeto Dimension para obter as configurações da tela*/
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        /*Ajusta o tamanho da tela*/
        this.setSize(dimension.width, dimension.height);

        /*Retira as bordas da janela, criando o modo FullScreen*/
        if(screenMode){
            this.setUndecorated(true);
        }else {
            this.setTitle("CG - Projeto Cubo");
        }

        /*Trata os eventos do teclado*/
        addKeyListener(this);

        /*Programação em Java3D*/
        universo = new VirtualUniverse();
        locale = new Locale(universo);

        /*Obtem caracteristicas do dispositivo grafico utilizado: monitor*/
        GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getBestConfiguration(g3d);

        /*Cria o objeto canvas3D*/
        canvas3D = new Canvas3D(gc);
        /*Ajusta a dimensao do canvas 3d */
        canvas3D.setSize(dimension.width, dimension.height);
        /*Adicionando o subgrafo de visualização*/
        locale.addBranchGraph(grafoVisualizacao());
        /*Adicionando o subgrafo de contexto*/
        locale.addBranchGraph(grafoContexto());

        /*Programação Java*/

        /*Adiciona o canvas3d ao tratador de eventos do teclado*/
        canvas3D.addKeyListener(this);
        /*Adiciona o canvas3d a janela e o exibe*/
        this.getContentPane().add(canvas3D);

        /*Trata o botao fechar no caso de modo janela*/
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    /*Método que cria o subgrafo de visualização*/
    public BranchGroup grafoVisualizacao(){
        /*Cria um objeto BranchGroup*/
        BranchGroup visualBG = new BranchGroup();
        /*cria um objeto View*/
        View visual = new View();
        /*Cria um objeto ViewPlataform*/
        ViewPlatform visualPlataform = new ViewPlatform();
        /*Cria um objeto PhysicalBody*/
        PhysicalBody physicalBody = new PhysicalBody (new Point3d(0, 0, 0), new Point3d(0, 0, 0));
        /*Cria um objeto Physical Environment*/
        PhysicalEnvironment ambiente = new PhysicalEnvironment ();
        /*Adiciona viewPlatform ao view*/
        visual.attachViewPlatform(visualPlataform);
        /*Adiciona physicalBody ao view*/
        visual.setPhysicalBody(physicalBody);
        /*Adiciona physicalEnvironment ao view*/
        visual.setPhysicalEnvironment(ambiente);
        /*Adiciona canvas ao view*/
        visual.addCanvas3D(canvas3D);

        /*Cria um objeto Transform3D */
        Transform3D transform3D = new Transform3D();
        /* Configura o Transform3D com o Vector3f*/
        transform3D.set (new Vector3f (0.0f, 0.0f, 10.0f));
        /*Cria um objeto TransformGroup*/
        TransformGroup visualPlatformTG = new TransformGroup(transform3D);
        /*Adiciona o ViewPlatform ao TransformGroup*/
        visualPlatformTG.addChild(visualPlataform);

        /*Adiciona o TransformGroup ao BranchGroup*/
        visualBG.addChild(visualPlatformTG);

        /*Retorna o BranchGroup contendo o subgrafo de visualização*/
        return visualBG;

    }

    /*Metodo que cria o subgrafo de contexto*/
    public BranchGroup grafoContexto () {
        /*Cria o branchgroup do subgrafo de contexto*/
        BranchGroup contextoBG = new BranchGroup();
        /*Cria um cubo colorido e o adiciona ao branchgroup do subgrafo de contexto*/
        cubo = new ColorCube();
        //contextoBG.addChild(cubo);
        //Cria grupo de transformações - Translação/Escala/Rotação
        TransformGroup tg = new TransformGroup();
        //Cria vetor de valor para transformações
        Vector3f transVector = new Vector3f();
        //cria objeto que projeta transformações 3D para o grupo de transformações
        Transform3D t3d = new Transform3D();


        //Translação nas posições (X, Y, Z)
        transVector.set(0.0f, 2.0f, 0.0f);
        //Adiciona o vetor de transformações criado ao projetor de transformações
        t3d.setTranslation(transVector);
        //Adiciona a transformação criada no grupo de transformações
        tg.setTransform(t3d);
        //Adiciona o objeto ao grupo de transformações
        tg.addChild(cubo);
        //Adiciona tudo no grafo de contexto
        contextoBG.addChild(tg);

        /*Retorna o BranchGroup contendo o subgrafo de contexto*/
        return contextoBG;
    }



    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }


}
