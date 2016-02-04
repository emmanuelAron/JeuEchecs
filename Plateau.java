

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextField;


/*
convention pour les coordonnées:-commence en haut à gauche
                                -va de 0 à 7
                                -i : lignes
                                -j : colonnes
                                -les lignes dans le crochet de gauche du tableau bidimensionnel
                                
convention pour les couleurs: 	
	-cases vues			: 	affiche des étoiles--->remplacer par des cases oranges!
	-marquer prenables	: 	colorie en bleu lorsque une pièce est prenable,rien sinon
	-rencontrer			:	colorie en vert clair lorsque la pièce peut se déplacer...et..
*/


public class Plateau extends JFrame implements ActionListener
{
	//tableau pour le jeu (64 cases,donc tableau de cases)
	 private Case[][] tab;
	 //boutons pour les cases vues,pour vider, et pour démarquer
	 JButton jbCasesVues,jbVider,jbDemarquer, jbRencontre , jbPiecesPrenables,jbMarquerPrenables,
	 				jbListePositionsPrenables, jbCasesPrenantes,jbEstEchec,jbListeAccessible,jbPat,jbMat;
	// JLabel ligne0,ligne1,ligne2,ligne3,ligne4,ligne5,ligne6,ligne7;
	 boolean estExecutee= false;//vaut vrai si demarquer() est executee
	 int ligneSelectionnee;
	 int colonneSelectionnee;
	
	Plateau()
	{
		int x = 0;
		int y = 0;
		int largeur = 40;/* largeur d'une jtf*/
		int largeurBouton = 130;
		int hauteurBouton = 30;
		int centrageX = 120;
		int centrageY = 40;
		int etiquetteX=60;
		int etiquetteY=40;
		tab = new Case[8][8];
		Case c=null;
		this.getContentPane().setLayout(null);
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				c = new Case(this);
				this.tab[i][j] = c;
				c.jt.setBounds(x+centrageX, y+centrageY, largeur, largeur);
				this.getContentPane().add(c.jt);c.setLigne(i);c.setColonne(j);
				x = x + largeur;
			}
			x = 0;
			y = y+largeur;
		}
		//les 3 boutons
		
		//cases vues
		this.jbCasesVues = new JButton("cases vues");
		this.jbCasesVues.setBounds(5,400,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbCasesVues);
		this.jbCasesVues.addActionListener(this);
		
		this.jbMarquerPrenables = new JButton("prenables");
		this.jbMarquerPrenables.setBounds(5,400+hauteurBouton+10,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbMarquerPrenables);
		this.jbMarquerPrenables.addActionListener(this);
		
		this.jbListeAccessible = new JButton("accessible");
		this.jbListeAccessible.setBounds(5,400+2*hauteurBouton+20,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbListeAccessible);
		this.jbListeAccessible.addActionListener(this);
		
		this.jbPat = new JButton("pat");
		this.jbPat.setBounds(largeurBouton,400+2*hauteurBouton+20,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbPat);
		this.jbPat.addActionListener(this);
		
		this.jbMat = new JButton("mat!");
		this.jbMat.setBounds(2*largeurBouton,400+2*hauteurBouton+20,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbMat);
		this.jbMat.addActionListener(this);
		
		this.jbEstEchec = new JButton("échec!");
		this.jbEstEchec.setBounds(largeurBouton,400+hauteurBouton+10,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbEstEchec);
		this.jbEstEchec.addActionListener(this);
		
		this.jbListePositionsPrenables = new JButton("liste prenables");
		this.jbListePositionsPrenables.setBounds(2*largeurBouton,400+hauteurBouton+10,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbListePositionsPrenables);
		this.jbListePositionsPrenables.addActionListener(this);
		
		this.jbCasesPrenantes = new JButton("cases prenantes");
		this.jbCasesPrenantes.setBounds(3*largeurBouton,400+hauteurBouton+10,largeurBouton,hauteurBouton);
		//this.x = this.x+this.largeur+intervalle;
		this.getContentPane().add(this.jbCasesPrenantes);
		this.jbCasesPrenantes.addActionListener(this);
		
		this.jbVider = new JButton("vider");
		this.jbVider.setBounds(largeurBouton,400,largeurBouton,hauteurBouton);
		this.getContentPane().add(jbVider);
		this.jbVider.addActionListener(this);
		//this.x = this.x+this.largeur+intervalle;
		
		this.jbDemarquer = new JButton("demarquer");
		this.jbDemarquer.setBounds(2*largeurBouton,400,largeurBouton,hauteurBouton);
		this.getContentPane().add(jbDemarquer);
		this.jbDemarquer.addActionListener(this);
		
		this.jbRencontre = new JButton("rencontrer");
		this.jbRencontre.setBounds(3*largeurBouton,400,largeurBouton,hauteurBouton);
		this.getContentPane().add(jbRencontre);
		this.jbRencontre.addActionListener(this);
		
		/*for(int i=0;i<8;i++)
		{
		this.ligne0 = new JLabel("ligne"+i+":");
		this.ligne0.setBounds(etiquetteX,etiquetteY+c.getJt().getY(),largeurBouton,hauteurBouton);
		this.getContentPane().add(ligne0);
		}*/
		
//		this.ligne0 = new JLabel("ligne 1");
//		this.ligne0.setBounds(etiquetteX,etiquetteY,largeurBouton,hauteurBouton);
//		this.getContentPane().add(ligne0);
//		
//		this.ligne0 = new JLabel("ligne 2");
//		this.ligne0.setBounds(etiquetteX,etiquetteY,largeurBouton,hauteurBouton);
//		this.getContentPane().add(ligne0);
//		
//		this.ligne0 = new JLabel("ligne 3");
//		this.ligne0.setBounds(etiquetteX,etiquetteY,largeurBouton,hauteurBouton);
//		this.getContentPane().add(ligne4);
//		
//		this.ligne0 = new JLabel("ligne 5");
//		this.ligne0.setBounds(etiquetteX,etiquetteY,largeurBouton,hauteurBouton);
//		this.getContentPane().add(ligne0);
//		
//		this.ligne0 = new JLabel("ligne 0");
//		this.ligne0.setBounds(etiquetteX,etiquetteY,largeurBouton,hauteurBouton);
//		this.getContentPane().add(ligne0);
//		
//		this.ligne0 = new JLabel("ligne 0");
//		this.ligne0.setBounds(etiquetteX,etiquetteY,largeurBouton,hauteurBouton);
//		this.getContentPane().add(ligne0);
		
		/*this.jbPiecesPrenables = new JButton("pièces prenables");
		this.jbPiecesPrenables.setBounds(20+4*largeurBouton,400,largeurBouton,hauteurBouton);
		this.getContentPane().add(jbPiecesPrenables);
		this.jbPiecesPrenables.addActionListener(this);*/
		//this.x = this.x+this.largeur+intervalle;
		
		this.getContentPane().setBackground(Color.gray);
		this.setBounds(50,50,600,600);
		this.setTitle(this.getClass().getName());
		this.setVisible(true);
	}
	public Case[][] getTab() 
	{
		return tab;
	}
	public void setTab(Case[][] tab) 
	{
		this.tab = tab;
	}
	
	public void poser(String piece,int ligne,int colonne)
	{
		this.tab[ligne-1][colonne-1].piece = piece;
		this.tab[ligne-1][colonne-1].jt.setText(piece);
	}
	//traitement du pion
	public void avanceUneCaseBlanc(Position p,Color c)
	{
		if(this.couleur(p)=='b') this.ajouteCaseColoree(new Position(p.getLigne()-1	, p.getColonne()),c );
	}
	public void avanceDeuxCasesBlanc(Position p,Color c)
	{
		if(this.couleur(p)=='b') this.ajouteCaseColoree(new Position(p.getLigne()-2	, p.getColonne()),c );
	}
	public void avanceUneCaseNoir(Position p,Color c)
	{
		if(this.couleur(p)=='n') this.ajouteCaseColoree(new Position(p.getLigne()+1	, p.getColonne()),c );
	}
	public void avanceDeuxCasesNoir(Position p,Color c)
	{
		if(this.couleur(p)=='n') this.ajouteCaseColoree(new Position(p.getLigne()+2	, p.getColonne()),c );
	}
	
	public void pionDeplacement(Position p,Color c)
	{
			if(p.getLigne()!=6)
				this.avanceUneCaseBlanc(p, c);
			else 
			{	
				this.avanceDeuxCasesBlanc(p, c);
				this.avanceUneCaseBlanc(p, c.PINK);
			}
			if(p.getLigne()!=1)
				this.avanceUneCaseNoir(p, c);
			else 
			{
				this.avanceDeuxCasesNoir(p, c);
				this.avanceUneCaseNoir(p, c.PINK);
			}
	}
	//ajoute une étoile pour la position (i,j)
	public void ajouteCaseColoree(Position p,Color c)
	{
		if(p.getLigne()>=0 && p.getLigne()<=7 && p.getColonne()>=0 && p.getColonne()<=7)
		this.tab[p.getLigne()][p.getColonne()].jt.setBackground(c);
	}
	public void ajouteLigneColoreeEst(Position p, Color col)
	{
			for(int j=p.getColonne()+1;j<8;j++)//vers l'est
			{
				this.ajouteCaseColoree(new Position(p.getLigne(),j), col);
			}
	}
	public void ajouteLigneColoreeOuest(Position p, Color col)
	{
			for(int j=p.getColonne()-1;j>=0;j--)//ouest
			{
				this.ajouteCaseColoree(new Position(p.getLigne(),j), col);
			}
	}
	public void ajouteLigneColoreeNord(Position p,Color col)
	{
				for(int i=p.getLigne()-1;i>=0;i--)//nord
				{
					this.ajouteCaseColoree(new Position(i,p.getColonne()), col);
				}
	}
	public void ajouteLigneColoreeSud(Position p,Color col)
	{
			for(int i=p.getLigne()+1;i<8;i++)/* sud */
			{
				this.ajouteCaseColoree(new Position(i,p.getColonne()), col);
			}
	}
	public void ajouteLigneColoreeSudEst(Position p,Color col)
	{
			int i=p.getLigne()+1;
			for(int j=p.getColonne()+1;j<=8;j++)
			{
				this.ajouteCaseColoree(new Position(i,j), col);
				i++;
			}
	}
	public void ajouteLigneColoreeSudOuest(Position p,Color col)
	{
			int i=p.getLigne()+1;
			for(int j=p.getColonne()-1;j>=0;j--)
			{
				this.ajouteCaseColoree(new Position(i,j), col);
				i++;
			}
	}
	public void ajouteLigneColoreeNordOuest(Position p,Color col)
	{
			int i=p.getLigne()-1;
			for(int j=p.getColonne()-1;j>=0;j--)
			{
				
				this.ajouteCaseColoree(new Position(i,j), col);
				i--;
			}
	}
	public void ajouteLigneColoreeNordEst(Position p,Color col)
	{
			int i=p.getLigne()-1;
			for(int j=p.getColonne()+1;j<=8;j++)
			{
				this.ajouteCaseColoree(new Position(i,j), col);
				i--;
			}
	}
	/*
	 * a) affiche un segment coloré entre deux positions pour la direction sud
	 * ( en partant de p1.getLigne()+1 )
	 * p1:correspond à la pièce posée
	 * 
	 */
	public void segmentColoréSud(Position p1,Color col)
	{
		for(int i=p1.getLigne()+1;i<8;i++)/* sud */
		{
			if(this.tab[i][p1.getColonne()].jt.getText().equals(""))
			{
				this.ajouteCaseColoree(new Position(i,p1.getColonne()), col);
			}
			else break;
		}
	}
	public void segmentColoréNord(Position p1,Color col)
	{
		for(int i=p1.getLigne()-1;i>=0;i--)//nord
		{
			if(this.tab[i][p1.getColonne()].jt.getText().equals(""))
			{
				this.ajouteCaseColoree(new Position(i,p1.getColonne()), col);
			}
			else break;
		}
	}
	public void segmentColoréEst(Position p1,Color col)
	{
		for(int j=p1.getColonne()+1;j<8;j++)//vers l'est
		{
			if(this.tab[p1.getLigne()][j].jt.getText().equals(""))
			{	
				this.ajouteCaseColoree(new Position(p1.getLigne(),j), col);
			}
			else break;
		}
	}
	public void segmentColoréOuest(Position p1,Color col)
	{
		for(int j=p1.getColonne()-1;j>=0;j--)//ouest
		{
			if(this.tab[p1.getLigne()][j].jt.getText().equals(""))
			{
				this.ajouteCaseColoree(new Position(p1.getLigne(),j), col);
			}
			else break;
		}
	}
	public void segmentColoréSudEst(Position p, Color col)
	{
		try{
			int i=p.getLigne();
				for(int j=p.getColonne()+1;j<8;j++)
				{
					i++;
					if(this.tab[i][j].jt.getText().equals(""))
					{
						this.ajouteCaseColoree(new Position(i,j), col);
					}
					else break;
				}
			}
		catch(ArrayIndexOutOfBoundsException e){}
	}
	public void segmentColoréSudOuest(Position p, Color col)
	{
		try
		{
			int i=p.getLigne();
			for(int j=p.getColonne();j>0;j--)
			{
				i++;
				if(this.tab[i][j-1].jt.getText().equals(""))
				{
					this.ajouteCaseColoree(new Position(i,j-1), col);
				}
				else break;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
	}
	public void segmentColoréNordEst(Position p,Color col)
	{
		
			try{
				int i=p.getLigne();
				for(int j=p.getColonne();j<=8;j++)
				{
					i--;
					if(this.tab[i][j+1].jt.getText().equals(""))
					{
						this.ajouteCaseColoree(new Position(i,j+1), col);
					}
					else break;
				}
				}//fin du try
				catch(ArrayIndexOutOfBoundsException e){}
	}
	public void segmentColoréNordOuest(Position p,Color col)
	{
		try{
			int i=p.getLigne();
			for(int j=p.getColonne();j>0;j--)
			{
				i--;
				if(this.tab[i][j-1].jt.getText().equals("") )
				{
					this.ajouteCaseColoree(new Position(i,j-1), col);
				}
				else break;
			}
			}//fin du try
			catch(ArrayIndexOutOfBoundsException e){}
	}
	
	//en bleu lorsque une pièce est prenable,rien sinon
	public Position ListeMarquerPrenableLigneEst(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
			for(int j=p.getColonne()+1;j<8;j++)
			{
					if( !this.tab[p.getLigne()][j].jt.getText().equals("")  )
					{	
						if(this.couleur(new Position(p.getLigne(),j))!=coul)return new Position(p.getLigne() , j);
						return pos;
					}
			}
			return pos;
	}
	public Position ListeMarquerPrenableLigneOuest(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		for(int j=p.getColonne()-1;j>=0;j--)
		{
				if(!this.tab[p.getLigne()][j].jt.getText().equals("") && this.couleur(new Position(p.getLigne(), j))!=coul)
				{
					pos = new Position(p.getLigne() , j);return pos;
				}
		}
		return pos;
	}
	public Position ListeMarquerPrenableLigneNord(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		for(int i=p.getLigne()-1;i>=0;i--)
		{
				if(!this.tab[i][p.getColonne()].jt.getText().equals("")&& this.couleur(new Position(i, p.getColonne()))!=coul)
				{
					pos = new Position(i , p.getColonne());return pos;
				}
		}
		return pos;
	}
	public Position ListeMarquerPrenableLigneSud(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		for(int i=p.getLigne()+1;i<8;i++)
		{
				if(!this.tab[i][p.getColonne()].jt.getText().equals("")&& this.couleur(new Position(i, p.getColonne()))!=coul)
				{
					pos = new Position(i , p.getColonne());return pos;
				}
		}
		return pos;
	}
	public Position ListeMarquerPrenableLigneSudOuest(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
		//direction sud-ouest
		int i=p.getLigne();
			for(int j=p.getColonne();j>0;j--)
			{
				i++;
				if((!this.tab[i][j-1].jt.getText().equals("")) && this.couleur(new Position(i, (j-1)) ) !=coul)
				{
					pos = new Position(i , j-1);return pos;
				}
			}
		}//fin du try
		catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableLigneSudEst(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try{
			//direction sud-est
			int i=p.getLigne();
			for(int j=p.getColonne()+1;j<8;j++)
			{
				i++;
				if((!this.tab[i][j].jt.getText().equals("")) && this.couleur (new Position(i, j)) != coul)
				{
					pos = new Position(i , j);return pos;
				}
			}
			}//fin du try
			catch(ArrayIndexOutOfBoundsException e){}
			return pos;
	}
	public Position ListeMarquerPrenableLigneNordOuest(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try{
			int i=p.getLigne();
				for(int j=p.getColonne();j>0;j--)
				{
					i--;
					if((!this.tab[i][j-1].jt.getText().equals("")) && this.couleur( new Position(i, (j-1) )) != coul )
					{
						pos = new Position(i , j-1);return pos;
					}
				}
			}//fin du try
			catch(ArrayIndexOutOfBoundsException e){}
			return pos;
	}
	public Position ListeMarquerPrenableLigneNordEst(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try{
			int i=p.getLigne();
			for(int j=p.getColonne();j<=8;j++)
				{
					i--;
					if( (!this.tab[i][j+1].jt.getText().equals("")) && this.couleur(new Position(i, (j+1))) !=coul )
					{
						pos = new Position(i , j+1);return pos;
					}
				}
			}//fin du try
			catch(ArrayIndexOutOfBoundsException e){}
			return pos;
	}
	public Position ListeMarquerPrenableCaseN(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()-1),p.getColonne()))!=coul)
			{
				pos = new Position(p.getLigne()-1 ,p.getColonne());return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCaseNE(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()+1].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()-1),(p.getColonne()+1)))!=coul)
			{
				pos = new Position(p.getLigne()-1 ,p.getColonne()+1);return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
		return pos;
	}
	public Position ListeMarquerPrenableCaseNO(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()-1].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()-1),(p.getColonne()-1)))!=coul)
			{
				pos = new Position(p.getLigne()-1 ,p.getColonne()-1);return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCaseE(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()][p.getColonne()+1].jt.getText().equals("")&& this.couleur(new Position(p.getLigne(),(p.getColonne()+1)))!=coul )
			{
				pos = new Position(p.getLigne() ,p.getColonne()+1);return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
		return pos;
	}
	public Position ListeMarquerPrenableCaseO(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()][p.getColonne()-1].jt.getText().equals("") && this.couleur(new Position(p.getLigne(),(p.getColonne()-1)))!=coul)
			{
				pos = new Position(p.getLigne()-1 ,p.getColonne());return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCaseS(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if (!this.tab[p.getLigne()+1][p.getColonne()].jt.getText().equals("") && this.couleur(new Position((p.getLigne()+1),p.getColonne()))!=coul)
			{
				pos = new Position(p.getLigne()+1 ,p.getColonne());return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
		return pos;
	}
	public Position ListeMarquerPrenableCaseSE(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{   
			if(! this.tab[p.getLigne()+1][p.getColonne()+1].jt.getText().equals("") && this.couleur(new Position((p.getLigne()+1), (p.getColonne()+1)))!=coul )
			{
				pos = new Position(p.getLigne()+1 ,p.getColonne()+1);return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCaseSO(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()+1][p.getColonne()-1].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()+1),(p.getColonne()-1)))!=coul)
			{
				pos = new Position(p.getLigne()+1 ,p.getColonne()-1);return pos;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCavalierplus1plus2(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()+1][p.getColonne()+2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()+1, p.getColonne()+2)) !=coul)
			{
				pos =new Position(p.getLigne()+1 , p.getColonne()+2);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCavaliermoins1plus2(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()+2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-1, p.getColonne()+2)) !=coul)
			{
				pos =new Position(p.getLigne()-1 , p.getColonne()+2);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCavalierplus1moins2(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()+2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-1, p.getColonne()+2)) !=coul)
			{
				pos =new Position(p.getLigne()-1 , p.getColonne()+2);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	public Position ListeMarquerPrenableCavaliermoins2moins1(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-2][p.getColonne()-1].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-2, p.getColonne()-1)) !=coul)
			{
				pos =new Position(p.getLigne()-2 , p.getColonne()-1);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	//-2 +1
	public Position ListeMarquerPrenableCavaliermoins2plus1(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-2][p.getColonne()+1].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-2, p.getColonne()+1)) !=coul)
			{
				pos =new Position(p.getLigne()-2 , p.getColonne()+1);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	//+2 -1
	public Position ListeMarquerPrenableCavalierplus2moins1(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()+2][p.getColonne()-1].jt.getText().equals("") && this.couleur(new Position(p.getLigne()+2, p.getColonne()-1)) !=coul)
			{
				pos =new Position(p.getLigne()+2 , p.getColonne()-1);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	//+2 , +1
	public Position ListeMarquerPrenableCavalierplus2plus1(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()+2][p.getColonne()+1].jt.getText().equals("") && this.couleur(new Position(p.getLigne()+2, p.getColonne()+1)) !=coul)
			{
				pos =new Position(p.getLigne()+2 , p.getColonne()+1);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}
	
	public Position ListeMarquerPrenableCavaliermoins1moins2(Position p)
	{
		Position pos = new Position(-1,-1);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()-2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-1, p.getColonne()-2)) !=coul)
			{
				pos =new Position(p.getLigne()-1 , p.getColonne()-2);return pos;
			}
		}catch(ArrayIndexOutOfBoundsException e){}
		return pos;
	}

	public void MarquerPrenableLigneEst(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
			for(int j=p.getColonne()+1;j<8;j++)
			{
					if( !this.tab[p.getLigne()][j].jt.getText().equals("")  && this.couleur(new Position(p.getLigne(),j))!=coul)
					{	
						this.tab[p.getLigne()][j].jt.setBackground(Color.blue);
					}
			}
	}
	public void MarquerPrenableLigneOuest(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		for(int j=p.getColonne()-1;j>=0;j--)
		{
				if(!this.tab[p.getLigne()][j].jt.getText().equals("") && this.couleur(new Position(p.getLigne(), j))!=coul)
				{
					this.tab[p.getLigne()][j].jt.setBackground(Color.blue);
				}
		}
	}
	public void MarquerPrenableLigneNord(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		for(int i=p.getLigne()-1;i>=0;i--)
		{
				if(!this.tab[i][p.getColonne()].jt.getText().equals("")&& this.couleur(new Position(i, p.getColonne()))!=coul)
				{
					this.tab[i][p.getColonne()].jt.setBackground(Color.blue);
				}
		}
	}
	public void MarquerPrenableLigneSud(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		for(int i=p.getLigne()+1;i<8;i++)
		{
				if(!this.tab[i][p.getColonne()].jt.getText().equals("")&& this.couleur(new Position(i, p.getColonne()))!=coul)
				{
					this.tab[i][p.getColonne()].jt.setBackground(Color.blue);
				}
		}
	}
	public void MarquerPrenableLigneSudOuest(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
		//direction sud-ouest
		int i=p.getLigne();
			for(int j=p.getColonne();j>0;j--)
			{
				i++;
				//int j0 = j-1;
				if((!this.tab[i][j-1].jt.getText().equals("")) && this.couleur(new Position(i, (j-1)) ) !=coul)
				{
				this.tab[i][j-1].jt.setBackground(Color.blue);
				}
				
			}
		}//fin du try
		catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableLigneSudEst(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try{
			//direction sud-est
			int i=p.getLigne();
			for(int j=p.getColonne()+1;j<8;j++)
			{
				i++;
				if((!this.tab[i][j].jt.getText().equals("")) && this.couleur (new Position(i, j)) != coul)
				{
				this.tab[i][j].jt.setBackground(Color.blue);
				}
			}
			}//fin du try
			catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableLigneNordOuest(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try{
			int i=p.getLigne();
				for(int j=p.getColonne();j>0;j--)
				{
					i--;
					if((!this.tab[i][j-1].jt.getText().equals("")) && this.couleur( new Position(i, (j-1) )) != coul )
					{
						this.tab[i][j-1].jt.setBackground(Color.blue);
					}
				}
			}//fin du try
			catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableLigneNordEst(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try{
			int i=p.getLigne();
			for(int j=p.getColonne();j<=8;j++)
				{
					i--;
					if( (!this.tab[i][j+1].jt.getText().equals("")) && this.couleur(new Position(i, (j+1))) !=coul )
					{
						this.tab[i][j+1].jt.setBackground(Color.blue);
					}
				}
			}//fin du try
			catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableCaseN(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()-1),p.getColonne()))!=coul)
			{
				this.tab[p.getLigne()-1][p.getColonne()].jt.setBackground(Color.blue);//N
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableCaseNE(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()+1].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()-1),(p.getColonne()+1)))!=coul)
			{
				this.tab[p.getLigne()-1][p.getColonne()+1].jt.setBackground(Color.blue);//NE
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
	}
	public void MarquerPrenableCaseNO(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()-1][p.getColonne()-1].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()-1),(p.getColonne()-1)))!=coul)
			{
				this.tab[p.getLigne()-1][p.getColonne()-1].jt.setBackground(Color.blue);//NO
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableCaseE(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()][p.getColonne()+1].jt.getText().equals("")&& this.couleur(new Position(p.getLigne(),(p.getColonne()+1)))!=coul )
			{
				this.tab[p.getLigne()][p.getColonne()+1].jt.setBackground(Color.blue);//E
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
	}
	public void MarquerPrenableCaseO(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()][p.getColonne()-1].jt.getText().equals("") && this.couleur(new Position(p.getLigne(),(p.getColonne()-1)))!=coul)
			{
				this.tab[p.getLigne()][p.getColonne()-1].jt.setBackground(Color.blue);//O(ouest)
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableCaseS(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if (!this.tab[p.getLigne()+1][p.getColonne()].jt.getText().equals("") && this.couleur(new Position((p.getLigne()+1),p.getColonne()))!=coul)
			{
			this.tab[p.getLigne()+1][p.getColonne()].jt.setBackground(Color.blue);//S
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
	}
	public void MarquerPrenableCaseSE(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{   
			if(! this.tab[p.getLigne()+1][p.getColonne()+1].jt.getText().equals("") && this.couleur(new Position((p.getLigne()+1), (p.getColonne()+1)))!=coul )
			{
				this.tab[p.getLigne()+1][p.getColonne()+1].jt.setBackground(Color.blue);//SE
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
	}
	public void MarquerPrenableCaseSO(Position p)
	{
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		try
		{
			if(!this.tab[p.getLigne()+1][p.getColonne()-1].jt.getText().equals("")&& this.couleur(new Position((p.getLigne()+1),(p.getColonne()-1)))!=coul)
			{
				this.tab[p.getLigne()+1][p.getColonne()-1].jt.setBackground(Color.blue);//SO
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
	}
	
	
	/************************************************************************************************************************
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param p: la position, numéro de ligne-0 à 7-
	 * @param c: la couleur de la case
	 ************************************************************************************************************************
	 */
	
	public void switchPiecePosee(Position p,Color c)
	{
		char piecePosee=this.tab[p.getLigne()][p.getColonne()].jt.getText().charAt(0);
		switch(piecePosee)
		{
		case 'c':
			{
				this.ajouteCaseColoree(new Position(p.getLigne()+2, p.getColonne()+1) , c);//SE
				this.ajouteCaseColoree(new Position(p.getLigne()+2, p.getColonne()-1) , c);//SO
				this.ajouteCaseColoree(new Position(p.getLigne()-2, p.getColonne()+1) , c);//NE
				this.ajouteCaseColoree(new Position(p.getLigne()-2, p.getColonne()-1) , c);//NO
				this.ajouteCaseColoree(new Position(p.getLigne()+1, p.getColonne()-2) , c);//Ouest sup
				this.ajouteCaseColoree(new Position(p.getLigne()-1, p.getColonne()-2) , c);//Ouest inf
				this.ajouteCaseColoree(new Position(p.getLigne()+1, p.getColonne()+2) , c);//Est sup
				this.ajouteCaseColoree(new Position(p.getLigne()-1, p.getColonne()+2) , c);//Est inf
				break;
			}
		case 'd':
		{
			this.ajouteLigneColoreeSud(p,c);
			this.ajouteLigneColoreeNord(p,c);
			this.ajouteLigneColoreeEst(p,c);
			this.ajouteLigneColoreeOuest(p,c);
			this.ajouteLigneColoreeNordOuest(p,c);
			this.ajouteLigneColoreeNordEst(p,c);
			this.ajouteLigneColoreeSudOuest(p,c);
			this.ajouteLigneColoreeSudEst(p,c);
			break;
		}
			
		case 't':
			{
				this.ajouteLigneColoreeSud(p,c);
				this.ajouteLigneColoreeNord(p,c);
				this.ajouteLigneColoreeEst(p,c);
				this.ajouteLigneColoreeOuest(p,c);
				break;
			}
		case 'f':
			{
				
				this.ajouteLigneColoreeNordOuest(p,c);
				this.ajouteLigneColoreeNordEst(p,c);
				this.ajouteLigneColoreeSudOuest(p,c);
				this.ajouteLigneColoreeSudEst(p,c);
				break;
			}
		case 'r':
			{
				this.ajouteCaseColoree(new Position(p.getLigne()-1	, p.getColonne()-1),c );
				this.ajouteCaseColoree(new Position(p.getLigne()-1	, p.getColonne()),c );
				this.ajouteCaseColoree(new Position(p.getLigne()-1	, p.getColonne()+1),c );
				this.ajouteCaseColoree(new Position(p.getLigne()	, p.getColonne()-1),c );
				this.ajouteCaseColoree(new Position(p.getLigne()	, p.getColonne()+1),c );
				this.ajouteCaseColoree(new Position(p.getLigne()+1	, p.getColonne()-1),c );
				this.ajouteCaseColoree(new Position(p.getLigne()+1	, p.getColonne()),c );
				this.ajouteCaseColoree(new Position(p.getLigne()+1	, p.getColonne()+1),c );
				break;
			}
		case 'p':
		{
			this.pionDeplacement(p,c);
		}
		
		}
	}
	
	//méthode qui vide l'échiquier
	public void viderEchiquier()
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				this.tab[i][j].jt.setText("");/*vide le contenu de chaque case  */
				this.tab[i][j].jt.setBackground(Color.WHITE);/* remet toutes les cases à blanc */
			}
		}
	}
	//méthode qui montre les cases vues
	public void casesVues()
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				this.tab[i][j].jt.setText("*");
			}
		}
	}
	//méthode qui enlève les étoiles mais garde les pièces
	public void demarquer()
	{
		estExecutee= true;
		//si le caractère(le contenu du jtf) est une étoile(i-e pas une pièce),alors on vide le jtf..
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				JTextField jtf2 = this.tab[i][j].jt;
				if(jtf2.getBackground()!= Color.WHITE)
				{
					jtf2.setBackground(Color.WHITE);
				}
			}
		}
	}//fin demarquer()
	public void switchPieceRencontree(Position p,Color c)
	{
		char piecePosee=this.tab[p.getLigne()][p.getColonne()].jt.getText().charAt(0);
		
		switch(piecePosee)
		{
		case 'c':
			{
			try{
				if(this.tab[p.getLigne()+2][p.getColonne()+1].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()+2, p.getColonne()+1) , c);//SE
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()+2][p.getColonne()-1].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()+2, p.getColonne()-1) , c);//SO
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()-2][p.getColonne()+1].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()-2, p.getColonne()+1) , c);//NE
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()-2][p.getColonne()-1].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()-2, p.getColonne()-1) , c);//NO
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()-2][p.getColonne()-1].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()-2, p.getColonne()-1) , c);//Ouest sup
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()-1][p.getColonne()-2].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()-1, p.getColonne()-2) , c);//Ouest inf
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()+1][p.getColonne()+2].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()+1, p.getColonne()+2) , c);//Est sup
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()-1][p.getColonne()+2].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()-1, p.getColonne()+2) , c);//Est inf
				
			}catch(ArrayIndexOutOfBoundsException e){}
			try{
				if(this.tab[p.getLigne()+1][p.getColonne()-2].jt.getText().equals(""))
					this.ajouteCaseColoree(new Position(p.getLigne()+1, p.getColonne()-2) , c);
				
			}catch(ArrayIndexOutOfBoundsException e){}
			break;
			}
		case 'd':
			{
				this.segmentColoréSudEst(p,c);
				this.segmentColoréSudOuest(p,c);
				this.segmentColoréNordEst(p,c);
				this.segmentColoréNordOuest(p,c);
				this.segmentColoréSud(p, c);
				this.segmentColoréNord(p, c);
				this.segmentColoréEst(p, c);
				this.segmentColoréOuest(p, c);
				break;
			}
		case 'f':
			{
				this.segmentColoréSudEst(p,c);
				this.segmentColoréSudOuest(p,c);
				this.segmentColoréNordEst(p,c);
				this.segmentColoréNordOuest(p,c);
				break;
			}
		case 't':
			{
				this.segmentColoréSud(p, c);
				this.segmentColoréNord(p, c);
				this.segmentColoréEst(p, c);
				this.segmentColoréOuest(p, c);
				break;
			}
		case 'r':
			{
				try{
				if( this.tab[p.getLigne()-1][p.getColonne()-1].jt.getText().equals("") ) 
					this.ajouteCaseColoree(new Position(p.getLigne()-1, p.getColonne()-1),c );
				}catch(ArrayIndexOutOfBoundsException e){}
				
				try{
					if( this.tab[p.getLigne()-1][p.getColonne()].jt.getText().equals("") ) 
						this.ajouteCaseColoree(new Position(p.getLigne()-1, p.getColonne()),c );
				}catch(ArrayIndexOutOfBoundsException e){}
				
				try{
					if( this.tab[p.getLigne()-1][p.getColonne()+1].jt.getText().equals("") ) 
						this.ajouteCaseColoree(new Position(p.getLigne()-1, p.getColonne()+1),c );	
				}catch(ArrayIndexOutOfBoundsException e){}
				
				try{
					if( this.tab[p.getLigne()][p.getColonne()-1].jt.getText().equals("")  ) 
						this.ajouteCaseColoree(new Position(p.getLigne(), p.getColonne()-1),c );		
				}catch(ArrayIndexOutOfBoundsException e){}
				
				try{
					if( this.tab[p.getLigne()][p.getColonne()-1].jt.getText().equals("") ) 
						this.ajouteCaseColoree(new Position(p.getLigne(), p.getColonne()+1),c );		
				}catch(ArrayIndexOutOfBoundsException e){}
				
				try{
					if( this.tab[p.getLigne()+1][p.getColonne()-1].jt.getText().equals("") ) 
						this.ajouteCaseColoree(new Position(p.getLigne()+1, p.getColonne()-1),c );	
				}catch(ArrayIndexOutOfBoundsException e){}
				
				try{
					if( this.tab[p.getLigne()+1][p.getColonne()].jt.getText().equals("") ) 
						this.ajouteCaseColoree(new Position(p.getLigne()+1, p.getColonne()),c );		
				}catch(ArrayIndexOutOfBoundsException e){}
				
				try{
					if( this.tab[p.getLigne()+1][p.getColonne()+1].jt.getText().equals("") ) 
						this.ajouteCaseColoree(new Position(p.getLigne()+1, p.getColonne()+1),c );	
				}catch(ArrayIndexOutOfBoundsException e){}
				
				break;
			}//fin case roi
		case 'p':
		{
			if(this.couleur(p)=='b')
			{
				if( this.tab[p.getLigne()-2][p.getColonne()].jt.getText().equals("") ) 
				{
					if( this.tab[p.getLigne()-1][p.getColonne()].jt.getText().equals("") ) 
						this.pionDeplacement(p, c);
				}
				else if( this.tab[p.getLigne()-1][p.getColonne()].jt.getText().equals("") ) 
					this.avanceUneCaseBlanc(p, c);
			}
			if(this.couleur(p)=='n')
			{
				if( this.tab[p.getLigne()+2][p.getColonne()].jt.getText().equals("") ) 
				{
					if( this.tab[p.getLigne()+1][p.getColonne()].jt.getText().equals("") ) 
						this.pionDeplacement(p, c);
				}
				else if( this.tab[p.getLigne()+1][p.getColonne()].jt.getText().equals("") ) 
					this.avanceUneCaseNoir(p, c);
			}
		}
			
		}//fin switch
	}
	
	public void switchMarquerPrenables(Position p)
	{
		char piecePosee=this.tab[p.getLigne()][p.getColonne()].jt.getText().charAt(0);
		char coul = this.couleur(new Position(p.getLigne(),p.getColonne()));
		
		
			switch(piecePosee)
			{
				case 't':
				{
					this.MarquerPrenableLigneEst(p);
					this.MarquerPrenableLigneOuest(p);
					this.MarquerPrenableLigneNord(p);
					this.MarquerPrenableLigneSud(p);
				}
				case 'f':
				{
					this.MarquerPrenableLigneNordEst(p);
					this.MarquerPrenableLigneNordOuest(p);
					this.MarquerPrenableLigneSudEst(p);
					this.MarquerPrenableLigneSudOuest(p);
				}
				case 'd':
				{
					this.MarquerPrenableLigneEst(p);
					this.MarquerPrenableLigneOuest(p);
					this.MarquerPrenableLigneNord(p);
					this.MarquerPrenableLigneSud(p);
					this.MarquerPrenableLigneNordEst(p);
					this.MarquerPrenableLigneNordOuest(p);
					this.MarquerPrenableLigneSudEst(p);
					this.MarquerPrenableLigneSudOuest(p);
				}
				case 'c':
				{
							if(!this.tab[p.getLigne()+2][p.getColonne()-1].jt.getText().equals("") && this.couleur(new Position((p.getLigne()+2), p.getColonne()-1))!=coul)
							{
								this.tab[p.getLigne()+2][p.getColonne()-1].jt.setBackground(Color.blue);
							}
							if(!this.tab[p.getLigne()-2][p.getColonne()+1].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-2, p.getColonne()+1))!=coul)
							{
								this.tab[p.getLigne()-2][p.getColonne()+1].jt.setBackground(Color.blue);
							}
							if( !this.tab[p.getLigne()+2][p.getColonne()+1].jt.getText().equals("")  &&  this.couleur(new Position(p.getLigne()+2, p.getColonne()+1))!=coul)
							{
								this.tab[p.getLigne()+2][p.getColonne()+1].jt.setBackground(Color.blue);
							}
							if(!this.tab[p.getLigne()+1][p.getColonne()+2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()+1, p.getColonne()+2))!=coul)
							{
								this.tab[p.getLigne()+1][p.getColonne()+2].jt.setBackground(Color.blue);
							}
							if(!this.tab[p.getLigne()-1][p.getColonne()+2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-1, p.getColonne()+2))!=coul)
							{
								this.tab[p.getLigne()-1][p.getColonne()+2].jt.setBackground(Color.blue);
							}
							if(!this.tab[p.getLigne()+1][p.getColonne()-2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()+1, p.getColonne()-2))!=coul)
							{
								this.tab[p.getLigne()+1][p.getColonne()-2].jt.setBackground(Color.blue);
							}
							if(!this.tab[p.getLigne()-2][p.getColonne()-1].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-2, p.getColonne()-1))!=coul)
							{
								this.tab[p.getLigne()-2][p.getColonne()-1].jt.setBackground(Color.blue);
							}
							if(!this.tab[p.getLigne()-1][p.getColonne()-2].jt.getText().equals("") && this.couleur(new Position(p.getLigne()-1, p.getColonne()-2))!=coul)
							{
								this.tab[p.getLigne()-1][p.getColonne()-2].jt.setBackground(Color.blue);
							}
					}
				case 'r':
				{
					this.MarquerPrenableCaseNO(p);
					this.MarquerPrenableCaseN(p);
					this.MarquerPrenableCaseNE(p);
					this.MarquerPrenableCaseO(p);
					this.MarquerPrenableCaseE(p);
					this.MarquerPrenableCaseSE(p);
					this.MarquerPrenableCaseS(p);
					this.MarquerPrenableCaseSO(p);
				}
				case 'p':
				{
					int nord  = p.getLigne()-1;
					int ouest = p.getColonne()-1;
					int sud   = p.getLigne()+1;
					int est   = p.getColonne()+1;
					
					if(this.couleur(p)=='b')
					{
							if( !this.tab[nord][ouest].jt.getText().equals("")&& this.couleur(new Position(nord, ouest))=='n')
							{
								this.MarquerPrenableCaseNO(p);
							}
							if( !this.tab[nord][est].jt.getText().equals("")&& this.couleur(new Position(nord, est))=='n')
							{
								this.MarquerPrenableCaseNE(p);
							}
						
					}
					if(this.couleur(p)=='n')
					{
							if( !this.tab[sud][ouest].jt.getText().equals("")&& this.couleur(new Position(sud, ouest))=='b')
							{
								this.MarquerPrenableCaseSO(p);
							}
							if( !this.tab[sud][est].jt.getText().equals("")&& this.couleur(new Position(sud, est))=='b')
							{
								this.MarquerPrenableCaseSE(p);
							}
						
					}
					
				}
				
				/*
				 * if(this.couleur(p)=='b')
			{
				if( this.tab[p.getLigne()-2][p.getColonne()].jt.getText().isEmpty() ) 
				{
					if( this.tab[p.getLigne()-1][p.getColonne()].jt.getText().isEmpty() ) 
						this.pionDeplacement(p, c);
				}
				else if( this.tab[p.getLigne()-1][p.getColonne()].jt.getText().isEmpty() ) 
					this.avanceUneCaseBlanc(p, c);
			}
			if(this.couleur(p)=='n')
			{
				if( this.tab[p.getLigne()+2][p.getColonne()].jt.getText().isEmpty() ) 
				{
					if( this.tab[p.getLigne()+1][p.getColonne()].jt.getText().isEmpty() ) 
						this.pionDeplacement(p, c);
				}
				else if( this.tab[p.getLigne()+1][p.getColonne()].jt.getText().isEmpty() ) 
					this.avanceUneCaseNoir(p, c);
				 * 
				 */
				
			}
	}
	/*retourne, pour une position donnée(1 numéro de ligne et un numéro de colonne), une liste de positions qui correspond 
	 * aux coordonnées des pièces pouvant être prises par la pièce située sur la position p. 
	 * 
	 */
	public ListePositions listePositionsPrenables(Position p)
	{
		ListePositions resultat= new ListePositions();
		
		//java.lang.StringIndexOutOfBoundsException: String index out of range: 0 veut dire:
		//Le charAt pointe sur un emplacement en dehors de la string.
		if(this.tab[p.getLigne()][p.getColonne()].jt.getText().equals(""))return resultat;
		
		char piecePosee=this.tab[p.getLigne()][p.getColonne()].jt.getText().charAt(0);
		
		switch(piecePosee)
		{
		
		case 't':
		{
			if(!this.ListeMarquerPrenableLigneEst(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneEst(p));
			if(!this.ListeMarquerPrenableLigneOuest(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneOuest(p));
			if(!this.ListeMarquerPrenableLigneSud(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneSud(p));
			if(!this.ListeMarquerPrenableLigneNord(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneNord(p));
			return resultat;
		}
		case 'f':
		{
			if(!this.ListeMarquerPrenableLigneNordEst(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneNordEst(p));
			if(!this.ListeMarquerPrenableLigneNordOuest(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneNordOuest(p));
			if(!this.ListeMarquerPrenableLigneSudEst(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneSudEst(p));
			if(!this.ListeMarquerPrenableLigneSudOuest(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneSudOuest(p));
			return resultat;
		}
		case 'd':
		{
			if(!this.ListeMarquerPrenableLigneEst(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneEst(p));
			if(!this.ListeMarquerPrenableLigneOuest(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneOuest(p));
			if(!this.ListeMarquerPrenableLigneSud(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneSud(p));
			if(!this.ListeMarquerPrenableLigneNord(p).estNulle()) resultat.getVPositions().add(this.ListeMarquerPrenableLigneNord(p));
			if(!this.ListeMarquerPrenableLigneNordEst(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneNordEst(p));
			if(!this.ListeMarquerPrenableLigneNordOuest(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneNordOuest(p));
			if(!this.ListeMarquerPrenableLigneSudEst(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneSudEst(p));
			if(!this.ListeMarquerPrenableLigneSudOuest(p).estNulle())   resultat.getVPositions().add(this.ListeMarquerPrenableLigneSudOuest(p));
			return resultat;
		}
		case 'r':
		{
			if(!this.ListeMarquerPrenableCaseE(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseE(p));
			if(!this.ListeMarquerPrenableCaseN(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseN(p));
			if(!this.ListeMarquerPrenableCaseS(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseS(p));
			if(!this.ListeMarquerPrenableCaseO(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseO(p));
			if(!this.ListeMarquerPrenableCaseNE(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseNE(p));
			if(!this.ListeMarquerPrenableCaseNO(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseNO(p));
			if(!this.ListeMarquerPrenableCaseSE(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseSE(p));
			if(!this.ListeMarquerPrenableCaseSO(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseSO(p));
			return resultat;
		}
		case 'c':
			{
				if(!this.ListeMarquerPrenableCavaliermoins1moins2(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavaliermoins1moins2(p));
				if(!this.ListeMarquerPrenableCavaliermoins1plus2(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavaliermoins1plus2(p));
				if(!this.ListeMarquerPrenableCavalierplus1moins2(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavalierplus1moins2(p));
				if(!this.ListeMarquerPrenableCavalierplus1plus2(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavalierplus1plus2(p));
				if(!this.ListeMarquerPrenableCavaliermoins2moins1(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavaliermoins2moins1(p));
				if(!this.ListeMarquerPrenableCavaliermoins2plus1(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavaliermoins2plus1(p));
				if(!this.ListeMarquerPrenableCavalierplus2moins1(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavalierplus2moins1(p));
				if(!this.ListeMarquerPrenableCavalierplus2plus1(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCavalierplus2plus1(p));
				return resultat;
			}
		case 'p':
		{
			if(this.couleur(p)== 'b')
			{
				if(!this.ListeMarquerPrenableCaseNO(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseNO(p));
				if(!this.ListeMarquerPrenableCaseNE(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseNE(p));
			}
			if(this.couleur(p)== 'n')
			{
				if(!this.ListeMarquerPrenableCaseSO(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseSO(p));
				if(!this.ListeMarquerPrenableCaseSE(p).estNulle())	resultat.getVPositions().add(this.ListeMarquerPrenableCaseSE(p));
			}
		}
		
		}
		return resultat;
	}
		
	public ListePositions listePositionPrenantes(Position pos)
	{	
		ListePositions resultat=new ListePositions();
		ListePositions lp = new ListePositions();
		for(int i=0;i<8;i++) 
		   { 
		      for(int j=0;j<8;j++) 
		        { 
		             lp =   this.listePositionsPrenables(new Position(i,j));lp.afficher();
		            if ( lp.contient(pos)  )
		            {
		            	resultat.getVPositions().add( new Position(i,j) );System.out.println(1494);
		            }
		        }
		   }
		return resultat;
	}
	
	/*public ListePositions positionEchec(Position p)
	{
		String s = this.tab[p.getLigne()][p.getColonne()].jt.getText();
		ListePositions resultat = new ListePositions();
		try
		{   
			if( this.tab[p.getLigne()+1][p.getColonne()+1].jt.getText().equals("") )
			{
				this.tab[p.getLigne()+1][p.getColonne()+1].jt.setText(s);
				if(!this.estEnEchec(new Position(p.getLigne()+1,p.getColonne()+1)))
				
				resultat.getVPositions().add( new Position(p.getLigne()+1,p.getColonne()+1) );
				this.tab[p.getLigne()+1][p.getColonne()+1].jt.setText("");
				//a finir!
				return resultat;
			}
			return resultat;
		}
		catch(ArrayIndexOutOfBoundsException e){}
	}*/
	
	
	public ListePositions listePositionAccessibles(Position p)
	{
		ListePositions resultat = new ListePositions();
		int nord  = p.getLigne()-1;
		int ouest = p.getColonne()-1;
		int sud   = p.getLigne()+1;
		int est   = p.getColonne()+1;
		
		/*
		 * (attention à l'échec!)
		 * transformer la méthode Piecerencontree() en liste
		/*public void pieceRencontree(int ligne,int colonne)//permet de savoir si une piece obstrue le déplacement d'une autre.
		*/
			char piecePosee=this.tab[p.getLigne()][p.getColonne()].jt.getText().charAt(0);
			
			//tour
			if(piecePosee== 't'|| piecePosee == 'd')
			{
				for(int j=p.getColonne()+1;j<8;j++)//vers l'est
				{
					//si le jtf est vide on rajoute à la liste,sinon(ie:piece) on s'arrête
					if(this.tab[p.getLigne()][j].jt.getText().equals(""))
					{	
						resultat.getVPositions().add( new Position(p.getLigne(),j) );
					}
					else break;
				}
				for(int j=p.getColonne()-1;j>=0;j--)//ouest
				{
					if(this.tab[p.getLigne()][j].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne(),j) );
					}
					else break;//sinon:il y aurait des etoiles apres la piece rencontree
				}
				for(int i=p.getLigne()+1;i<8;i++)/* sud */
				{
					if(this.tab[i][p.getColonne()].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(i,p.getColonne()) );
					}
					else break;//sinon:il y aurait des etoiles apres la piece rencontree
				}
				for(int i=p.getLigne()-1;i>=0;i--)//nord
				{
					if(this.tab[i][p.getColonne()].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(i,p.getColonne()) );
					}
					else break;//sinon:il y aurait des etoiles apres la piece rencontree
				}
			}//fin tour
			
			//fou
			if(piecePosee=='f'|| piecePosee == 'd')
			{
				try{
				//direction sud-est
				int i=p.getLigne();
				for(int j=p.getColonne()+1;j<8;j++)
				{
					i++;
					if(this.tab[i][j].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(i,j) );
					}
					else break;
				}
				}//fin du try
				catch(ArrayIndexOutOfBoundsException e)
				{
				}
				try
					{
					//direction sud-ouest
					int i=p.getLigne();
						for(int j=p.getColonne();j>0;j--)
						{
							i++;
							if(this.tab[i][j-1].jt.getText().equals(""))
							{
								resultat.getVPositions().add( new Position(i,j-1) );
							}
							else break;
						}
					}//fin du try
					catch(ArrayIndexOutOfBoundsException e)
					{
					}
					//direction nord-ouest
					try{
						//direction nord-ouest
						int i=p.getLigne();
						for(int j=p.getColonne();j>0;j--)
						{
							i--;
							if(this.tab[i][j-1].jt.getText().equals("") )
							{
								resultat.getVPositions().add( new Position(i,j-1) );
							}
							else break;
						}
						}//fin du try
						catch(ArrayIndexOutOfBoundsException e){}
						try{
							//direction nord-est
							int i=p.getLigne();
							for(int j=p.getColonne();j<=8;j++)
							{
								i--;
								if(this.tab[i][j+1].jt.getText().equals(""))
								{
									resultat.getVPositions().add( new Position(i,j+1) );
								}
								else break;
							}
							}//fin du try
							catch(ArrayIndexOutOfBoundsException e){}
				}//fin du fou
			
			//roi
			if(piecePosee == 'r')
			{
				String s = this.tab[p.getLigne()][p.getColonne()].jt.getText();
				try
				{   
					if( this.tab[sud][est].jt.getText().equals("") )
					{
						this.tab[sud][est].jt.setText(s);
						
							{//si la position sud-est n'est pas en échec..
								if(!this.estEnEchec(new Position(sud,est)))
								{
									try
									{
									resultat.getVPositions().add( new Position(sud,est) );
									this.ajouteCaseColoree(new Position(sud,est), Color.CYAN);
									//this.tab[nord][ouest].jt.setText("");
									}catch(Exception e){}
								}
								else
								{
									this.tab[sud][est].jt.setText("");
									this.tab[nord][ouest].jt.setText(s);
									resultat.getVPositions().remove(new Position(nord,ouest));
								}
							}this.tab[sud][est].jt.setText("");
					}
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try
				{
					if(this.tab[nord][ouest].jt.getText().equals(""))
					{
						this.tab[nord][ouest].jt.setText(s);
						if(!this.estEnEchec(new Position(nord,ouest)))
						{
							try
							{
							resultat.getVPositions().add( new Position(nord,ouest) );
							this.ajouteCaseColoree(new Position(nord,ouest), Color.CYAN);
							}catch(Exception e){}
						}
						else
						{
							resultat.getVPositions().remove(new Position(nord,ouest));
							this.tab[nord][ouest].jt.setText("");
							this.tab[sud][est].jt.setText(s);
							resultat.getVPositions().remove(0);
							this.ajouteCaseColoree(new Position(sud,est), Color.WHITE);
							this.tab[sud][est].jt.setText("");
						}
						
					}this.tab[nord][ouest].jt.setText("");
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try
				{
					if(this.tab[sud][ouest].jt.getText().equals(""))
					{
						this.tab[sud][ouest].jt.setText(s);
						if(!this.estEnEchec(new Position(sud,ouest)))
						{
							try
							{
							resultat.getVPositions().add( new Position(sud,ouest) );
							this.ajouteCaseColoree(new Position(sud,ouest), Color.CYAN);
							}catch(Exception e){}
						}
						else
						{
							if(this.estAuBord(p)== false)
							{
								resultat.getVPositions().remove(new Position(sud,ouest));
								this.tab[sud][ouest].jt.setText("");
								this.tab[nord][est].jt.setText(s);
								resultat.getVPositions().remove(2);
								this.ajouteCaseColoree(new Position(nord,est), Color.WHITE);
								this.tab[nord][est].jt.setText("");
							}
							else
							{
								if(p.getLigne()==0)
								{
									this.tab[sud][est].jt.setText("");
									resultat.getVPositions().remove(new Position(sud,ouest));
									this.tab[sud][ouest].jt.setText("");
								}
								if(p.getColonne()==7)
								{
									resultat.getVPositions().remove(new Position(sud,ouest));
									this.tab[sud][ouest].jt.setText("");
								}
							}
						}
						//this.tab[sud][ouest].jt.setText("");
					}this.tab[sud][ouest].jt.setText("");
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try
				{
					if(this.tab[nord][est].jt.getText().equals(""))
					{
						this.tab[nord][est].jt.setText(s);
						if(!this.estEnEchec(new Position(nord,est) ))
						{
							try
							{
							resultat.getVPositions().add( new Position(nord,est) );
							this.ajouteCaseColoree(new Position(nord,est), Color.CYAN);
							}catch(Exception e){}
						}
						else
						{
							if(this.estAuBord(p)== false)
							{
									this.tab[nord][est].jt.setText("");
									this.tab[sud][ouest].jt.setText(s);
									resultat.getVPositions().remove(new Position(sud,ouest));
									this.tab[sud][ouest].jt.setText("");
									this.ajouteCaseColoree(new Position(sud,ouest), Color.WHITE);
									resultat.getVPositions().remove(2);
							}
							else
							{
								if(p.getLigne()==7)
								{
									this.tab[nord][est].jt.setText("");
									resultat.getVPositions().remove(new Position(nord,est));
								}
								if(p.getColonne()==0)
								{
									resultat.getVPositions().remove(new Position(sud,est));
									this.tab[sud][est].jt.setText("");
								}
							}
						}
					}this.tab[nord][est].jt.setText("");
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try
				{
					if(this.tab[p.getLigne()][est].jt.getText().equals(""))
					{
						this.tab[p.getLigne()][est].jt.setText(s);
						if(!this.estEnEchec(new Position(p.getLigne(),est)))
						{
						try
						{
							if(this.estAuBord(p)== false)
							{
								this.ajouteCaseColoree(new Position(p.getLigne(),est), Color.CYAN);
								resultat.getVPositions().add( new Position(p.getLigne(),est) );
								
							}
							else
							{
								if(p.getLigne()==7)
								{
									resultat.getVPositions().add( new Position(p.getLigne(),est) );
									this.ajouteCaseColoree(new Position(p.getLigne(),est), Color.CYAN);
								}
								else
								{
									resultat.getVPositions().add( new Position(p.getLigne(),est) );
									this.ajouteCaseColoree(new Position(p.getLigne(),est), Color.CYAN);
								}
							}
						}catch(Exception e){}
						}
						else
						{
							if(this.estAuBord(p)== false)
							{
								this.tab[p.getLigne()][est].jt.setText("");
								this.tab[p.getLigne()][ouest].jt.setText(s);
							}
							else
							{
								this.tab[sud][est].jt.setText("");
							}
							
						}
					}this.tab[p.getLigne()][est].jt.setText("");
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try
				{
					if(this.tab[p.getLigne()][ouest].jt.getText().equals(""))
					{
						this.tab[p.getLigne()][ouest].jt.setText(s);
						if(!this.estEnEchec(new Position(p.getLigne(),ouest)))
						{
							try{
							resultat.getVPositions().add( new Position(p.getLigne(),ouest) );
							this.ajouteCaseColoree(new Position(p.getLigne(),ouest), Color.CYAN);
							}catch(Exception e){}
						}
						else
						{
							this.tab[p.getLigne()][ouest].jt.setText("");
							this.tab[p.getLigne()][est].jt.setText(s);
							this.ajouteCaseColoree(new Position(p.getLigne(),ouest), Color.WHITE);
							this.tab[p.getLigne()][est].jt.setText("");
							this.ajouteCaseColoree(new Position(p.getLigne(),est), Color.WHITE);
							resultat.getVPositions().remove(4);
						}
					}this.tab[p.getLigne()][ouest].jt.setText("");
				}
				catch(ArrayIndexOutOfBoundsException e){}
				
				try
				{
					if(this.tab[nord][p.getColonne()].jt.getText().equals(""))
					{
						this.tab[nord][p.getColonne()].jt.setText(s);
						if(!this.estEnEchec(new Position(nord,p.getColonne())))
						{
							try
							{
							resultat.getVPositions().add( new Position(nord,p.getColonne()) );
							this.ajouteCaseColoree(new Position(nord,p.getColonne()), Color.CYAN);
							}catch(Exception e){}
						}
						else
						{
							this.tab[nord][p.getColonne()].jt.setText("");
							this.tab[sud][p.getColonne()].jt.setText(s);
							//this.ajouteCaseColoree(new Position(nord,p.getColonne()), Color.WHITE);
							//this.tab[sud][p.getColonne()].jt.setText("");
							//this.ajouteCaseColoree(new Position(sud,p.getColonne()), Color.WHITE);
							//resultat.getVPositions().aff
						}
						//this.tab[nord][p.getColonne()].jt.setText("");
					}this.tab[nord][p.getColonne()].jt.setText("");
				}
				catch(ArrayIndexOutOfBoundsException e){}
				try
				{
					if (this.tab[sud][p.getColonne()].jt.getText().equals(""))
					{
						this.tab[sud][p.getColonne()].jt.setText(s);
						if(!this.estEnEchec(new Position(sud,p.getColonne())))
						{
							try{
							resultat.getVPositions().add( new Position(sud,p.getColonne()) );
							this.ajouteCaseColoree(new Position(sud,p.getColonne()), Color.CYAN);
							}catch(Exception e){}
						}
						else
						{
							this.tab[sud][p.getColonne()].jt.setText("");
							this.tab[nord][p.getColonne()].jt.setText(s);
							this.ajouteCaseColoree(new Position(sud,p.getColonne()), Color.WHITE);
							this.tab[nord][p.getColonne()].jt.setText("");
							this.ajouteCaseColoree(new Position(nord,p.getColonne()), Color.WHITE);
							resultat.getVPositions().remove(6);
						}
						//this.tab[sud][p.getColonne()].jt.setText("");
					}this.tab[sud][p.getColonne()].jt.setText("");
				}
				catch(ArrayIndexOutOfBoundsException e){}
				
			}//fin du if roi
			//cavalier
			if(piecePosee=='c')
			{
				if(piecePosee== 'c')
				{
					if(this.tab[p.getLigne()+2][p.getColonne()+1].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()+2,p.getColonne()+1) );
					}
					if(this.tab[p.getLigne()+2][p.getColonne()-1].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()+2,p.getColonne()-1) );
					}
					if(this.tab[p.getLigne()-2][p.getColonne()+1].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()-2,p.getColonne()+1) );
					}
					if(this.tab[p.getLigne()+1][p.getColonne()+2].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()+1,p.getColonne()+2) );
					}
					if(this.tab[p.getLigne()-1][p.getColonne()+2].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()-1,p.getColonne()+2) );
					}
					if(this.tab[p.getLigne()+1][p.getColonne()-2].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()+1,p.getColonne()-2) );
					}
					if(this.tab[p.getLigne()-2][p.getColonne()-1].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()-2,p.getColonne()-1) );
					}
					if(this.tab[p.getLigne()-1][p.getColonne()-2].jt.getText().equals(""))
					{
						resultat.getVPositions().add( new Position(p.getLigne()-1,p.getColonne()-2) );
					}
				}
			}
		return resultat;
	}//fin listePositionsAccessibles()
	
	public ListePositions casesPrenantes(Position pos) 
	{
		ListePositions resultat= new ListePositions();
	    ListePositions lp= new ListePositions();
	   for(int i=0;i<8;i++) 
	   { 
	      for(int j=0;j<8;j++) 
	        { 
	             lp =   this.listePositionsPrenables(new Position(i,j));
	            if ( lp.contient(pos) /*on trouve pos dans lp */)
	            {
	            	/*on marque la case (i,j)*/
	            	this.tab[i][j].jt.setBackground(Color.cyan);//alors on marque la case (i,j)
	            	resultat.getVPositions().add(new Position(i,j));
	            }
	        }
	   }
	   return resultat;
	}
	
	char couleur(Position p)
	{
		if( this.tab[p.getLigne()][p.getColonne()].jt.getText().equals("") )
			return '?';
		
		return this.tab[p.getLigne()][p.getColonne()].jt.getText().charAt(1);
	}
	//méthode de l'échec:la position est celle du roi,retourne vrai si le roi est en échec
	public boolean estEnEchec(Position p)
	{
		boolean echec = false;
		/* si listePositionPrenantes(p) n'est pas vide, vrai */
		if (this.listePositionPrenantes(p).getVPositions().size()!= 0)
			echec = true;
		return echec;
	}
//	si la taille de la liste des positions accessibles est nulle, alors le roi est pat
	public boolean estPat(Position p)
	{
		boolean pat = false;
		if(this.tailleListeAccessibles(p)==0)
		{
			pat = true;
			System.out.println("le roi est pat "+pat);
		}
		else System.out.println("le roi n'est pas pat "+pat);
		return pat;
	}
	
	//l'échec et mat (la position est celle du roi)
	public boolean estMat(Position p)
	{
		boolean mat = false;
		if(this.estEnEchec(p)&& this.estPat(p))
		{
			if(this.tailleListeAccessibles(p)== 1)
			{
				mat = true;
				System.out.println("le roi est mat "+mat);
			}
		}
		else System.out.println("le roi n'est pas mat "+mat);
			
		return mat;
	}
	/*
	 * méthode qui retourne la position correspondante aux "rayons X de l'échec",
	 * c'est à dire à la position où ne peut pas aller un roi en situation d'échec et mat
	 * par la pièce qui le met en échec.(La position opposée au roi par rapport à l'échec
	 * correspond au "rayon X").
	 * 
	 */
	public Position rayonsXdelEchec(Position p)
	{
		//char pieceDonnantEchec=this.tab[p.getLigne()][p.getColonne()].jt.getText().charAt(0);
		ListePositions lp = new ListePositions();
		Position rayonX = new Position(-1,-1);
		if(this.estEnEchec(p)== true)
		{
			lp = casesPrenantes(p);
			for(int i=0;i<lp.getVPositions().size();i++)
			{
				
			}
		}
		
		return rayonX;
	}
	public boolean estAuBord(Position p)
	{
		boolean bord = false;
		if(p.getLigne()<=0 || p.getLigne()>=7 || p.getColonne()<=0 || p.getColonne()>=7)
			bord = true;
		else bord = false;
		
		return bord;
	}
	/*
	 * Si sud-ouest et nord-est sont libres:copie le roi sur sud-est,teste si il est en échec,....
	 */
	public boolean traverseRoiSEetNO(Position p)
	{
		boolean rr = false;
		
		return rr;
	}
	/*
	 * La case sud-est est libre si: elle n'est pas occupée par une autre pièce,
	 * elle n'est pas en dehors de l'échiquier
	 */
	public boolean estLibreSE(Position p)
	{
		int nord  = p.getLigne()-1;
		int ouest = p.getColonne()-1;
		int sud   = p.getLigne()+1;
		int est   = p.getColonne()+1;
		boolean libre = false;
		
		p = new Position(sud,est);
		
		if(tab[p.getLigne()][p.getColonne()].jt.getText()=="" && p.getLigne()<=7 && p.getColonne()<=7)
			libre = true;
		
		return libre;
	}
	public boolean estLibreSO(Position p)
	{
		int nord  = p.getLigne()-1;
		int ouest = p.getColonne()-1;
		int sud   = p.getLigne()+1;
		int est   = p.getColonne()+1;
		boolean libre = false;
		
		p = new Position(sud,ouest);
		
		if(tab[p.getLigne()][p.getColonne()].jt.getText()=="" && p.getLigne()<=7 && p.getColonne()>=0)
			libre = true;
		
		return libre;
	}
	public boolean estLibreNO(Position p)
	{
		int nord  = p.getLigne()-1;
		int ouest = p.getColonne()-1;
		int sud   = p.getLigne()+1;
		int est   = p.getColonne()+1;
		boolean libre = false;
		
		p = new Position(sud,ouest);
		
		if(tab[p.getLigne()][p.getColonne()].jt.getText()=="" && p.getLigne()>=0 && p.getColonne()>=0)
			libre = true;
		
		return libre;
	}
	
	//récupère les coordonnées de la position de la pièce mettant en échec le roi(à faire!)
	
	//case opposée du roi:
	//retourne pour chaque 
	
	
	//méthode qui retourne la taille de la liste des positions accessibles
	public int tailleListeAccessibles(Position p)
	{
		int taille = -1;
		taille = this.listePositionAccessibles(p).getVPositions().size();
		System.out.println("le nombre de positions accessibles est de :"+taille);
		return taille;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jbVider)
		{
			this.viderEchiquier();
		}
		if(e.getSource()==jbCasesVues)
		{
			this.switchPiecePosee(new Position(this.ligneSelectionnee,this.colonneSelectionnee), Color.orange);	
		}
		if(e.getSource()==jbDemarquer)
		{
			this.demarquer();
		}
		if(e.getSource()==jbRencontre)
		{
			//this.pieceRencontree( new Position(this.ligneSelectionnee,this.colonneSelectionnee));
			this.switchPieceRencontree(new Position(this.ligneSelectionnee,this.colonneSelectionnee), Color.green);
		}
		/*if(e.getSource()== jbPiecesPrenables)
		{
			this.piecesPrenables1(this.ligneSelectionnee,this.colonneSelectionnee);
		}*/
		if(e.getSource()==jbMarquerPrenables)
		{
			//this.marquerPrenables(new Position(this.ligneSelectionnee, this.colonneSelectionnee));
			this.switchMarquerPrenables(new Position(this.ligneSelectionnee, this.colonneSelectionnee));
		}
		if(e.getSource()==jbCasesPrenantes)
		{
			this.casesPrenantes(new Position(this.ligneSelectionnee,this.colonneSelectionnee)).afficher();
		}
		if(e.getSource()==jbListePositionsPrenables)
		{
		this.listePositionsPrenables(new Position(this.ligneSelectionnee,this.colonneSelectionnee)).afficher();
		}
		if(e.getSource()==jbEstEchec)
		{
		System.out.println(this.estEnEchec( new Position(this.ligneSelectionnee,this.colonneSelectionnee) ) );
		}
		if(e.getSource()==jbListeAccessible)
		{
			this.listePositionAccessibles(new Position(this.ligneSelectionnee,this.colonneSelectionnee)).afficher();
			//this.tailleListeAccessibles(  new Position(this.ligneSelectionnee,this.colonneSelectionnee));
		}
		if(e.getSource()==jbPat)
		{
			this.estPat(new Position(this.ligneSelectionnee,this.colonneSelectionnee));
		}
		if(e.getSource()==jbMat)
		{
			this.estMat(new Position(this.ligneSelectionnee,this.colonneSelectionnee));
		}
	}//fin actionPerformed()
	
	public static void main(String[] args) 
	{
		Plateau p = new Plateau();
		System.out.println("accessible pour le roi:SE/NO,SO/NE:ok(sauf bords..)");
		System.out.println("ok: nord,sud ;pb pour est/ouest:n'est pas en échec");
		//Position po = new Position(2,9);
		//System.out.println(p.estAuBord(po));
		//Position po = new Position(4,4);
		//p.tab[4][4].jt.setText("tb");
		//System.out.println(po.recupCaractere());
		//p.tab[4][2].jt.setText("tn");
		//ListePositions lp = p.listePositionsPrenables(po);
		//lp.afficher();
		/*Position po = new Position(4,4);
		ListePositions lp = p.listePositionsPrenables(po);
		lp.afficher();*/
	}
}//fin classe
