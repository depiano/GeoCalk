function parseIt(xml)
	{
	console.log("INIZIO PARSER");
			if(schema==2)
			{
				//console.log("VEDO LO SCHEMA 2");
				pulisci_area_paper(schema);
				crea_paper(schema);
				clear(schema);

					var i=0;
					var t=0;
					var s;
					//Ciclo su tutte le entità
					$(xml).find('packagedElement').each(function()
					{
							//Controllo che sia un'entità
							if($(this).attr('xmi:type')=="uml:Class")
							{
									//Oggetto dove memorizzo tutti i dati per poi creare l'elemento
									var info = {};
									info.oggetto = null;

									if($(this).attr('type')==undefined)
										info.type = "entity";
									else
										info.type="WeakEntity";
									info.schema="2";
									info.selected=0;
									info.name=$(this).attr('name');
									//console.log("Nome Entità letta ="+info.name);

									info.idDettaglio=$(this).find("details").attr("xmi:id");//Aggiunta di Renato Veneruso per prelevare dallo xmi l'id di details dell'entità ,usato quando utilizzo le funzioni per salvare le posizioni
									info.idEAnnotation=$(this).find("eAnnotations").attr("xmi:id");//Aggiunta di Renato Veneruso per prelevare dallo xmi l'id di eAnnotation dell'entità ,usato quando utilizzo le funzioni per salvare le posizioni


									//Aggiunta di Bernardo: 1 -> puo' essere aggiunta all'ontologia come una semplice entita'
									//						0 -> non deve essere aggiunta come una semplice entita', bensi' come
									//							 entita' padre oppure entita' figlia
									//DEFAULT: 1
									info.adding = 1;



									//Parte modificata da Renato Veneruso per far si che si possa leggere dallo xmi i dati sulla posizione
									//Si fa in modo che si legga tra gli attributi dell entità gli attributi x ed y.Se sono presenti
									//si usano quei valori altrimenti si usa il metodo di base gia esistente
									  if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined){
									info.x=parseInt($(this).attr('x'));// ottimo funziona
									info.y=parseInt($(this).attr('y'));


									  }else{
										  info.x=parseInt(pos_el[i].x);
									info.y=parseInt(pos_el[i].y);
									  }

									info.cod="j_"+count;

									//Aggiungo l'oggetto alla mappa di tutti gli elementi
									map[info.cod] = info;

									//array codice entità -> oggetto (viene utilizzato per tener conto delle entità da
									//rappresentare nello schema 2
									ent_sc2[info.cod]=info;

									//Contatore per il codice identificativo degli elementi
									count++;

									//array nome entità -> codice dell'entità (viene utilizzato successivamente per le relazioni)
									entita2[$(this).attr('name')]=info.cod;

									var j=0;

									var attributi=new Array(); //Tutti gli attributi dell'entità info.cod
									//Codice dell'entità di riferimento di tutti gli attributi presenti nell'array attributi
									var cod_ent=info.cod;

									$(this).children("ownedAttribute").each(function()
									{

										if($(this).attr('xmi:type')=="uml:Property" && ($(this).attr('association')==undefined))
										{
											if(j>=50)
											{
												alert("Non posso rappresentare quest'attr. \n\nHai superato il numero max");
												return;
											}


											//Oggetto attributo
											var info = {};
											info.oggetto = null;
											info.type = "attribute";
											info.schema="2";
											info.selected=0;


											//posizione dell'attributo
											//Parte modificata da Renato Veneruso riguardante il posizionamento degli attributi.

											if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined ){
											  var x=parseInt($(this).attr('x'));
											var y=parseInt($(this).attr('y'));

											}else{
												var x=pos_el[i].x+pos_attr[j].x;
											var y=pos_el[i].y-pos_attr[j].y;
											}

											info.x=parseInt(x);
											info.y=parseInt(y);
											info.name=$(this).attr('name');
											//console.log("attr ="+info.name);
											info.cod= "j_"+count;
											info.cod_entity=cod_ent;



											if($(this).find("details").attr("key")=="PK")
													info.key=1;
											else
													info.key=0;

											map[info.cod] = info;

											attributi.push(info);

											count++;
											j++;
										}
									});
									//cod entity -> array di tutti i suoi attributi
									attr_sc2[cod_ent]=attributi;

									i++;
							}
					});

					$(xml).find('packagedElement').each(function()
					{
							if($(this).attr('xmi:type')=="uml:Association")
							{
								//controlla se una relazione è già stata considerata
								var res=$(this).attr('xmi:id').split("__");
								if(controlla_rl(res[0],res[1],schema))
								{
									var r = {x:res[0],y:res[1]};
									rl.push(r);

									//Crea oggetto relazione
									var info = {};
									info.oggetto = null;

									if($(this).attr('type')==undefined)
										info.type ="relation";
									else
										info.type="ISA";

									info.schema="2";
									info.selected=0;
									//In base al nome dell'entità recupero il suo codice identificativo
									info.cod_ent1=entita2[res[0]];
									info.cod_ent2=entita2[res[1]];
									//console.log("Relazione tipo ="+info.type+ " tra "+res[0]+ " e "+res[1]);
									if(res.length>3)
										info.name=res[2];
									//console.log("Nome Relazione ="+info.name);
									info.cod="j_"+count;

									// Posizione della relazione Modificata Da Renato Veneruso
									//
									if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined){
										info.x=parseInt($(this).attr('x'));
										info.y=parseInt($(this).attr('y'));

									}else{
									info.x=parseInt(pos_el[t].x+250);
									info.y=parseInt(pos_el[t].y);

									}

									map[info.cod] = info;

									relation_sc2[info.cod]=info;

									count++;

									var j=0;

									var attributi=new Array(); //Tutti gli attributi dell'entità info.cod
									//Codice dell'entità di riferimento di tutti gli attributi presenti nell'array attributi
									var cod_rel=info.cod;

									$(this).children("ownedAttribute").each(function()
									{

										if($(this).attr('xmi:type')=="uml:Property" && ($(this).attr('association')==undefined))
										{
											if(j>=50)
											{
												alert("Non posso rappresentare quest'attr. \n\nHai superato il numero max");
												return;
											}
											//console.log("VEDO UN ATTRIBUTO SULLA RELAZIONE, OVVERO: "+$(this).attr('name'));



											//Oggetto attributo
											var info = {};
											info.oggetto = null;
											info.type = "attribute";
											info.schema="2";
											info.selected=0;

                                          //posizione dell'attributo
											if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined){
										var x=parseInt($(this).attr('x'));
										var y=parseInt($(this).attr('y'));

									}else{

											var x=pos_el[t].x+pos_attr[j].x;
											var y=pos_el[t].y-pos_attr[j].y;
									}

											info.x=parseInt(x);
											info.y=parseInt(y);
											info.name=$(this).attr('name');
											//console.log("attr ="+info.name);



											info.cod= "j_"+count;
											info.cod_entity=cod_rel;
											info.key=0;

											map[info.cod] = info;

											attributi.push(info);

											count++;
											j++;
										}
									});
									//cod entity -> array di tutti i suoi attributi
									attr_sc2[cod_rel]=attributi;

									//count++;
									t++;
								}
							}
					});
					callParserServlet(ent_sc2, relation_sc2, attr_sc2);
					disegna(schema);
			}
			else
			{
				pulisci_area_paper(schema);
				crea_paper(schema);
				clear(schema);
					var i=0;
					var t=0;
					var s;
					//Ciclo su tutte le entità
					$(xml).find('packagedElement').each(function()
					{
							//Controllo che sia un'entità
							if($(this).attr('xmi:type')=="uml:Class")
							{
								//Oggetto dove memorizzo tutti i dati per poi creare l'elemento
									var info = {};
									info.oggetto = null;

									if($(this).attr('type')==undefined)
										info.type = "entity";
									else
										info.type="WeakEntity";
									info.schema="1";
									info.selected=0;
									info.name=$(this).attr('name');

									info.idDettaglio=$(this).find("details").attr("xmi:id");//Aggiunta di Renato Veneruso per prelevare dallo xmi l'id di details dell'entità ,usato quando utilizzo le funzioni per salvare le posizioni
									info.idEAnnotation=$(this).find("eAnnotations").attr("xmi:id");//Aggiunta di Renato Veneruso per prelevare dallo xmi l'id di eAnnotation dell'entità ,usato quando utilizzo le funzioni per salvare le posizioni

									info.name=$(this).attr('name');
									//Aggiunta di Bernardo: 1 -> puo' essere aggiunta all'ontologia come una semplice entita'
									//						0 -> non deve essere aggiunta come una semplice entita', bensi' come
									//							 entita' padre oppure entita' figlia
									//DEFAULT: 1
									info.adding = 1;
									//Parte modificata da Renato Veneruso per far si che si possa leggere dallo xmi i dati sulla posizione
									//Si fa in modo che si legga tra gli attributi dell entità gli attributi x ed y.Se sono presenti
									//si usano quei valori altrimenti si usa il metodo di base gia esistente
									  if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined){
									info.x=parseInt($(this).attr('x'));// ottimo funziona
									info.y=parseInt($(this).attr('y'));


									  }else{
										  info.x=parseInt(pos_el[i].x);
									info.y=parseInt(pos_el[i].y);
									  }

									info.cod="j_"+count;

									//Aggiungo l'oggetto alla mappa di tutti gli elementi
									map[info.cod] = info;

									//arrat codice entità -> oggetto (viene utilizzato per tener conto delle entità da
									//rappresentare nello schema 1
									ent_sc1[info.cod]=info;

									count++;

									//array nome entità -> codice dell'entità (viene utilizzato successivamente per le relazioni)
									entita1[$(this).attr('name')]=info.cod;

									var j=0;

									var attributi=new Array(); //Tutti gli attributi dell'entità info.cod
									//Codice dell'entità di riferimento di tutti gli attributi presenti nell'array attributi
									var cod_ent=info.cod;

									$(this).children("ownedAttribute").each(function()
									{
										if($(this).attr('xmi:type')=="uml:Property" && ($(this).attr('association')==undefined))
										{
											if(j>=50)
											{
												alert("Non posso rappresentare quest'attr. \n\nHai superato il numero max");
												return;
											}





											//Oggetto attributo
											var info = {};
											info.oggetto = null;
											info.type = "attribute";
											info.schema="1";
											info.selected=0;

											//posizione dell'attributo
											//Modificato Da Renato Veneruso
											//Si utilizzano le posizioni x ed Y degli attributi se sono presenti, altrimenti si usa il metodo di posizionamento
											// di base
											if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined ){
											  var x=parseInt($(this).attr('x'));
											var y=parseInt($(this).attr('y'));

											}else{
												var x=pos_el[i].x+pos_attr[j].x;
											var y=pos_el[i].y-pos_attr[j].y;
											}

											info.x=parseInt(x);
											info.y=parseInt(y);
											info.name=$(this).attr('name');
											info.cod= "j_"+count;



											info.cod_entity=cod_ent;

											if($(this).find("details").attr("key")=="PK")
													info.key=1;
											else
													info.key=0;
											//console.log("VERIFICA NOME RELAZIONE: codice attr "+cod_ent);
											//console.log("VERIFICA NOME RELAZIONE: codice attr "+info.cod);
											//console.log("VERIFICA NOME RELAZIONE: codice rel "+info.cod_entity);
											//console.log("VERIFICA NOME RELAZIONE: "+map[info.cod_entity].cod+" "+map[info.cod_entity].name+" DI ATTR: "+$(this).attr('name'));
											//console.log("VERIFICA NOME RELAZIONE: "+map[cod_ent].name+" DI ATTR: "+$(this).attr('name'));
											map[info.cod] = info;

											attributi.push(info);

											count++;
											j++;
										}
									});

									//cod entity -> array di tutti i suoi attributi
									attr_sc1[cod_ent]=attributi;
									i++;
							}
					});

					$(xml).find('packagedElement').each(function()
					{
							if($(this).attr('xmi:type')=="uml:Association")
							{

								//controlla se una relazione è già stata considerata
								var res=$(this).attr('xmi:id').split("__");
								if(controlla_rl(res[0],res[1],schema))
								{
									var r = {x:res[0],y:res[1]};
									rl.push(r);

									//Crea oggetto relazione
									var info = {};
									info.oggetto = null;

									if($(this).attr('type')==undefined)
										info.type ="relation";
									else
										info.type="ISA";

									info.schema="1";
									info.selected=0;
									//In base al nome dell'entità recupero il suo codice identificativo
									info.cod_ent1=entita1[res[0]];
									info.cod_ent2=entita1[res[1]];
									//console.log("Relazione tipo ="+info.type+ " tra "+res[0]+ " e "+res[1]);
									if(res.length>3)
										info.name=res[2];
									//console.log("Nome Relazione ="+info.name);
									info.cod="j_"+count;
									// Posizione della relazione Modificata Da Renato Veneruso
									//
									if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined){
										info.x=parseInt($(this).attr('x'));
										info.y=parseInt($(this).attr('y'));

									}else{
									info.x=parseInt(pos_el[t].x+250);
									info.y=parseInt(pos_el[t].y);

									}

									map[info.cod] = info;

									relation_sc1[info.cod]=info;

									count++;

									var j=0;
									//console.log("DEFINISCO J");

									var attributi=new Array(); //Tutti gli attributi dell'entità info.cod
									//Codice dell'entità di riferimento di tutti gli attributi presenti nell'array attributi
									var cod_rel=info.cod;

									$(this).children("ownedAttribute").each(function()
									{

										if($(this).attr('xmi:type')=="uml:Property" && ($(this).attr('association')==undefined))
										{
											if(j>=50)
											{
												alert("Non posso rappresentare quest'attr. \n\nHai superato il numero max");
												return;
											}
											//console.log("VEDO UN ATTRIBUTO SULLA RELAZIONE, OVVERO: "+$(this).attr('name'));
											//posizione dell'attributo



											//Oggetto attributo
											var info = {};
											info.oggetto = null;
											info.type = "attribute";
											info.schema="1";
											info.selected=0;

											// Posizione della relazione Modificata Da Renato Veneruso

									if($(this).attr('x')!=undefined && $(this).attr('y')!=undefined){
										info.x=parseInt($(this).attr('x'));
										info.y=parseInt($(this).attr('y'));

									}else{

											info.x=pos_el[t].x+pos_attr[j].x;
											info.y=pos_el[t].y-pos_attr[j].y;
									}


											info.name=$(this).attr('name');
											//console.log("attr ="+info.name);
											info.cod= "j_"+count;



											info.cod_entity=cod_rel;
											//console.log("VERIFICA NOME RELAZIONE: codice attr "+cod_rel);
											//console.log("VERIFICA NOME RELAZIONE: codice attr "+info.cod);
											//console.log("VERIFICA NOME RELAZIONE: codice rel "+info.cod_entity);
											//console.log("VERIFICA NOME RELAZIONE: "+map[info.cod_entity].cod+" "+map[info.cod_entity].name+" DI ATTR: "+$(this).attr('name'));
											//console.log("VERIFICA NOME RELAZIONE: "+map[cod_rel].name+" DI ATTR: "+$(this).attr('name'));
											info.key=0;

											map[info.cod] = info;
											//console.logconsole.log("VERIFICA NOME RELAZIONE: codice attr "+info.cod);
											//console.log("VERIFICA NOME RELAZIONE: codice rel "+info.cod_entity);
											//console.log("VERIFICA NOME RELAZIONE: "+map[info.cod_entity].cod+" "+map[info.cod_entity].name+" DI ATTR: "+$(this).attr('name'));
											attributi.push(info);

											count++;
											j++;
										}
									});
									//cod entity -> array di tutti i suoi attributi
									attr_sc1[cod_rel]=attributi;

									//count++;

									t++;
								}
							}
					});
					callParserServlet(ent_sc1, relation_sc1, attr_sc1);
					disegna(schema);
			}
		}


		/*
		Author: Antonio De Piano
		web site: www.depiano.it
		eMail: depianoantonio@gmail.com
		date: 10-08-2018
		Object: Load suorce target schema
		*/


		function loadSourceTargetSchema()
		{
			pulisci_area_paper(2);
			crea_paper(2);
			clear(2);
			disegna(2);

		}



	function disegna(schema)
	{
		if(schema==1)
		{
			for (var key in ent_sc1)
			{
				var e;
				if(ent_sc1[key].type=="entity")
					e = element(erd.Entity, ent_sc1[key].x, ent_sc1[key].y,ent_sc1[key].name,schema);
				else
					e=element(erd.WeakEntity, ent_sc1[key].x, ent_sc1[key].y,ent_sc1[key].name,schema);
				e.attr('cod', ent_sc1[key].cod);
				e.attr('text/font-size', 20);
				ent_sc1[key].oggetto=e;
			}

			var isa=new Object();

			for (var key in relation_sc1)
			{
				var e;
				if(relation_sc1[key].type=="relation")
				{
					if(relation_sc1[key].name!=null){
						e = element(erd.Relationship, relation_sc1[key].x, relation_sc1[key].y,relation_sc1[key].name,schema);
						//console.log("IF VERIFICATO");
						//console.log("Nome: "+relation_sc2[key].name);
					}
					else
						e = element(erd.Relationship, relation_sc1[key].x, relation_sc1[key].y,map[relation_sc1[key].cod_ent1].name+"\n"+map[relation_sc1[key].cod_ent2].name,schema);
					e.attr('cod', relation_sc1[key].cod);
					relation_sc1[key].oggetto=e;
					e.attr('text/font-size', 20);
					//link(map[relation_sc1[key].cod_ent1].oggetto,e);
					//link(e,map[relation_sc1[key].cod_ent2].oggetto);

					 var link = new joint.dia.Link({
									source: { id: map[relation_sc1[key].cod_ent1].oggetto.id },
									target: { id: e.id }
									});

									graph.addCells([map[relation_sc1[key].cod_ent1].oggetto, e, link]);

									var link = new joint.dia.Link({
									source: { id: e.id },
									target: { id: map[relation_sc1[key].cod_ent2].oggetto.id}
									});

									graph.addCells([e, map[relation_sc1[key].cod_ent2].oggetto, link]);
				}
				else if(relation_sc1[key].type=="ISA")
				{
					var info = {};
					info.oggetto = new Array;
					info.key_relation=key;
					isa[relation_sc1[key].cod_ent1] = info;
				}
			}

			for(var key in relation_sc1)
			{
				if(relation_sc1[key].type=="ISA")
				{
					isa[relation_sc1[key].cod_ent1].oggetto.push(relation_sc1[key].cod_ent2);
				}
			}

			for(key in isa)
			{
					var e;
					e = element(erd.ISA, relation_sc1[isa[key].key_relation].x, relation_sc1[isa[key].key_relation].y,"",schema);
					e.rotate(180);
					e.attr('cod', relation_sc1[isa[key].key_relation].cod);
					relation_sc1[isa[key].key_relation].oggetto=e;

					e.attr('text/font-size', 20);

					var link = new joint.dia.Link({
										source: { id: map[key].oggetto.id },
										target: { id: e.id }
										});

										graph.addCells([map[key].oggetto, e, link]);

					for(var w=0;w<isa[key].oggetto.length;w++)
					{
										var link = new joint.dia.Link({
										source: { id: e.id },
										target: { id: map[isa[key].oggetto[w]].oggetto.id}
										});

										graph.addCells([e,map[isa[key].oggetto[w]].oggetto, link]);
					}
			}
			for (var key in attr_sc1)
			{
				for(var i=0;i<attr_sc1[key].length;i++)
				{
					var s;
					if(attr_sc1[key][i].key==1)
							s = element(erd.Key,attr_sc1[key][i].x,attr_sc1[key][i].y, attr_sc1[key][i].name,schema);
					else
							s = element(erd.Normal,attr_sc1[key][i].x,attr_sc1[key][i].y, attr_sc1[key][i].name,schema);
					s.attr('text/font-size', 20);
					s.resize(150, 50);
					s.attr('cod', attr_sc1[key][i].cod);
					attr_sc1[key][i].oggetto=s;
					//link(map[attr_sc1[key][i].cod_entity].oggetto,s);

					var link = new joint.dia.Link({
												source: { id: map[attr_sc1[key][i].cod_entity].oggetto.id },
												target: { id: s.id }
											});
											//console.log("SOURCE" +map[attr_sc1[key][i].cod_entity].name);
											//console.log("TARGET"+attr_sc1[key][i].name);
											graph.addCells([map[attr_sc1[key][i].cod_entity].oggetto, s, link]);
				}
			}
		}
		else
		{
			flag=false;
			for (var key in ent_sc2)
			{
				if(!flag)
				{
					var employee = new erd.Entity({

					 position: { x: 100, y: 200 },
					 attrs: {
							 text: {
									 fill: '#ffffff',
									 text: 'Employee',
									 letterSpacing: 0,
									 style: { textShadow: '1px 0 1px #333333' }
							 },
							 '.outer': {
									 fill: '#31d0c6',
									 stroke: 'none',
									 filter: { name: 'dropShadow',  args: { dx: 0.5, dy: 2, blur: 2, color: '#333333' }}
							 },
							 '.inner': {
									 fill: '#31d0c6',
									 stroke: 'none',
									 filter: { name: 'dropShadow',  args: { dx: 0.5, dy: 2, blur: 2, color: '#333333' }}
							 }
					 }
			 });

			 flag=true;
				}

				var e;
				if(ent_sc2[key].type=="entity")
					e = element(erd.Entity, ent_sc2[key].x, ent_sc2[key].y,ent_sc2[key].name,schema);
				else
					e = element(erd.WeakEntity, ent_sc2[key].x, ent_sc2[key].y,ent_sc2[key].name,schema);
				e.attr('cod', ent_sc2[key].cod);
				e.attr('text/font-size', 20);
				ent_sc2[key].oggetto=e;
			}

			var isa=new Object();

			for (var key in relation_sc2)
			{
				var e;
				if(relation_sc2[key].type=="relation")
				{

					if(relation_sc2[key].name!=null){
						e = element(erd.Relationship, relation_sc2[key].x, relation_sc2[key].y,relation_sc2[key].name,schema);
						//console.log("IF VERIFICATO");
						//console.log("Nome: "+relation_sc2[key].name);
					}
					else
						e = element(erd.Relationship, relation_sc2[key].x, relation_sc2[key].y,map[relation_sc2[key].cod_ent1].name+"\n"+map[relation_sc2[key].cod_ent2].name,schema);
					e.attr('cod', relation_sc2[key].cod);
					relation_sc2[key].oggetto=e;
					e.attr('text/font-size', 20);
					//link(map[relation_sc2[key].cod_ent1].oggetto,e);
					//link(e,map[relation_sc2[key].cod_ent2].oggetto);

					 var link = new joint.dia.Link({
									source: { id: map[relation_sc2[key].cod_ent1].oggetto.id },
									target: { id: e.id }
									});

									graph2.addCells([map[relation_sc2[key].cod_ent1].oggetto, e, link]);

									var link = new joint.dia.Link({
									source: { id: e.id },
									target: { id: map[relation_sc2[key].cod_ent2].oggetto.id}
									});

									graph2.addCells([e, map[relation_sc2[key].cod_ent2].oggetto, link]);
				}
				else if(relation_sc2[key].type=="ISA")
				{
					//alert(relation_sc2[key].cod_ent1+" --- "+relation_sc2[key].cod_ent1);
					var info = {};
					info.oggetto = new Array;
					info.key_relation=key;
					isa[relation_sc2[key].cod_ent1] = info;
				}
			}

			for(var key in relation_sc2)
			{
				if(relation_sc2[key].type=="ISA")
				{
					isa[relation_sc2[key].cod_ent1].oggetto.push(relation_sc2[key].cod_ent2);
				}
			}

			for(key in isa)
			{
				var e;
				e = element(erd.ISA, relation_sc2[isa[key].key_relation].x, relation_sc2[isa[key].key_relation].y,"",schema);
				e.rotate(180);
				e.attr('cod', relation_sc2[isa[key].key_relation].cod);
				relation_sc2[isa[key].key_relation].oggetto=e;
				e.attr('text/font-size', 20);
					var link = new joint.dia.Link({
										source: { id: map[key].oggetto.id },
										target: { id: e.id }
										});

										graph2.addCells([map[key].oggetto, e, link]);

					for(var w=0;w<isa[key].oggetto.length;w++)
					{
										var link = new joint.dia.Link({
										source: { id: e.id },
										target: { id: map[isa[key].oggetto[w]].oggetto.id}
										});

										graph2.addCells([e,map[isa[key].oggetto[w]].oggetto, link]);
					}
			}


			for (var key in attr_sc2)
			{
				for(var i=0;i<attr_sc2[key].length;i++)
				{
					var s;
					if(attr_sc2[key][i].key==1)
							s = element(erd.Key,attr_sc2[key][i].x,attr_sc2[key][i].y, attr_sc2[key][i].name,schema);
					else
							s = element(erd.Normal,attr_sc2[key][i].x,attr_sc2[key][i].y, attr_sc2[key][i].name,schema);
					s.attr('text/font-size', 20);
					s.resize(150, 50);
					s.attr('cod', attr_sc2[key][i].cod);
					attr_sc2[key][i].oggetto=s;
					//link(map[attr_sc2[key][i].cod_entity].oggetto,s);

					//console.log("codSource: "+map[attr_sc2[key][i].cod_entity]);
					var link = new joint.dia.Link({
												source: { id: map[attr_sc2[key][i].cod_entity].oggetto.id },
												target: { id: s.id }
											});
											graph2.addCells([map[attr_sc2[key][i].cod_entity].oggetto, s, link]);

				}
			}
		}
	}