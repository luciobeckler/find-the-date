import java.util.List;
import java.util.Random;

import javax.management.relation.Role;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
        private Parser parser;
        private Jogador jogador;
        private Regiao currentRoom;
        private Date date;
        private Regiao barreiro, centro, oeste, pampulha, vendaNova, leste;
        private Roles parqueJacques,
                        shoppingDelRey,
                        barDoGanso,
                        lab,
                        mercadoCentral,
                        ccbb,
                        miranteMangabeiras,
                        pracaDoPapa,
                        chalezinho,
                        raveParty,
                        shoppingEstacao,
                        estacaoMoveVilarinho,
                        parqueMinicipalFazendaLagoaDoNado,
                        barMuseuClubeDaEsquina,
                        sescPalladium,
                        igrejinhaDaPampulha,
                        calouradaUFMG,
                        zoologicoBeloHorizonte;
        private Presente boneViseiraEsportiva, livroAutografado, garrafaRoyalSalute, copoPersonalizado, discoDeVinil,
                        quadroDecorativo, chaveiro, lataRefrigerante, buqueDeFlor, grudeTattoo, camisaDeTime,
                        ingressosBalada,
                        pinBroche, filtroDosSonhos, incenso, terco, ingressosOpenBar, bichoPelucia, copoStanley;
        private Date Maria,
                        Lucas,
                        Sofia,
                        Ana,
                        Joao,
                        Pedro,
                        Rafael;

        /**
         * Create the game and initialise its internal map.
         */
        public Game() {
                createPresentes();
                createDates();
                createRoles();
                createRegioes();

                parser = new Parser();
                jogador = new Jogador();
        }

        /**
         * Main play routine. Loops until end of play.
         */
        public void play() {
                printWelcome();

                // Enter the main command loop. Here we repeatedly read commands and
                // execute them until the game is over.

                boolean finished = false;
                while (!finished) {
                        Command command = parser.getCommand();
                        finished = processCommand(command);
                }
                System.out.println("Thank you for playing.  Good bye.");
        }

        /**
         * Given a command, process (that is: execute) the command.
         * 
         * @param command The command to be processed.
         * @return true If the command ends the game, false otherwise.
         */
        private boolean processCommand(Command command) {
                boolean wantToQuit = false;

                if (command.isUnknown()) {
                        System.out.println("I don't know what you mean...");
                        return false;
                }

                String commandWord = command.getCommandWord();
                if (commandWord.equals("help")) {
                        printHelp();
                } else if (commandWord.equals("go")) {
                        goRegiao(command);
                } else if (commandWord.equals("quit")) {
                        wantToQuit = quit(command);
                } else if (commandWord.equals("enter")) {
                        enterRole(command); // !Criar função para entrar no rolê
                }

                return wantToQuit;
        }

        /**
         * Print out the opening message for the player.
         */
        private void printWelcome() {
                System.out.println();
                System.out.println("Bem vindo ao desafio Find the Date!");
                System.out.println(
                                "Neste desafio você terá que realizar uma pesquisa de campo para descobrir qual o melhor rolê para levar a sua paixão! E como você deseja impressiona-la, escolha um presente que você ache que combine com ela.");
                System.out.println("");
                System.out.println("Para obter ajuda digite 'help'");
                System.out.println();

                geraDateAleatorio();
                geraRoleAleatorio(); // !Continuar daqui, gerar role aleatorio, implementar funcao "remember", "pick
                                     // present", "pick role" e "choose"

                System.out.println("Você iniciou sua busca na região: " + currentRoom.getNome()
                                + "! \nSegue a lista de rolês, Para entrar em um rolê desta região, digite \"go numero\".");
                System.out.println("");
                for (int i = 0; i < currentRoom.getListaRoles().size(); i++) {
                        System.out.println(i + " - " + currentRoom.getListaRoles().get(i).getNome());

                }
                System.out.print("Para sair desta região digite para onde deseja ir: ");
                if (currentRoom.getNorthExit() != null) {
                        System.out.print("north ");
                }
                if (currentRoom.getNorthExit() != null) {
                        System.out.print("east ");
                }
                if (currentRoom.getSouthExit() != null) {
                        System.out.print("south ");
                }
                if (currentRoom.getWestExit() != null) {
                        System.out.print("west ");
                }
                System.out.println();
        }

        private void geraDateAleatorio() {
                // *Gera um date aleatório
                Date[] listaDates = { Maria, Lucas, Sofia, Ana, Joao, Pedro, Rafael };
                Random randomDate = new Random();
                date = listaDates[randomDate.nextInt(listaDates.length)];
                System.out.println("\n\n******************************");
                System.out.println("O date sorteado foi:" + date.getNome());
                System.out.println("******************************\n");
                System.err.println(date.getDescricao());
                System.out.println("Digite \"remember date \" para relembrar de seu date!");
        }

        // implementations of user commands:

        /**
         * Print out some help information.
         * Here we print some stupid, cryptic message and a list of the
         * command words.
         */
        private void printHelp() {
                System.out.println("You are lost. You are alone. You wander");
                System.out.println("around at the university.");
                System.out.println();
                System.out.println("Your command words are:");
                System.out.println("   go enter quit help");
        }

        /**
         * Try to go in one direction. If there is an exit, enter
         * the new room, otherwise print an error message.
         */
        private void goRegiao(Command command) {
                if (!command.hasSecondWord()) {
                        // if there is no second word, we don't know where to go...
                        System.out.println("Go where?");
                        return;
                }

                String direction = command.getSecondWord();

                // Try to leave current room.
                Regiao nextRoom = null;
                if (direction.equals("north")) {
                        nextRoom = currentRoom.getNorthExit();
                }
                if (direction.equals("east")) {
                        nextRoom = currentRoom.getEastExit();
                }
                if (direction.equals("south")) {
                        nextRoom = currentRoom.getSouthExit();
                }
                if (direction.equals("west")) {
                        nextRoom = currentRoom.getWestExit();
                }

                if (nextRoom == null) {
                        System.out.println("There is no door!");
                } // !Criar um menu para o usuário escolher os rolês
                else {
                        currentRoom = nextRoom;
                        System.out.println("Você chegou na região " + currentRoom.getNome()
                                        + "! \nSegue a lista de rolês, Para entrar em um rolê desta região, digite \"go numero\".");
                        System.out.println("");
                        for (int i = 0; i < currentRoom.getListaRoles().size(); i++) {
                                System.out.println(i + " - " + currentRoom.getListaRoles().get(i).getNome());

                        }

                        System.out.print("Exits: ");
                        if (currentRoom.getNorthExit() != null) {
                                System.out.print("north ");
                        }
                        if (currentRoom.getEastExit() != null) {
                                System.out.print("east ");
                        }
                        if (currentRoom.getSouthExit() != null) {
                                System.out.print("south ");
                        }
                        if (currentRoom.getWestExit() != null) {
                                System.out.print("west ");
                        }
                        System.out.println();
                }
        }

        private void enterRole(Command command) {
                if (!command.hasSecondWord()) {
                        // if there is no second word, we don't know where to go...
                        System.out.println("Enter where?");
                        return;
                }

                String direction = command.getSecondWord();
                Regiao nextRoom = null;
                // Try to leave current room.
                if (Integer.parseInt(direction) + 1 > currentRoom.getListaRoles().size()
                                || Integer.parseInt(direction) + 1 < 0) {
                        nextRoom = null;
                } else {
                        Roles role = currentRoom.getListaRoles().get(Integer.parseInt(direction));
                        System.err.println(role.getNome());
                        System.err.println(role.getDescricao());
                }

                // !Modelo
                if (direction.equals("north")) {
                        nextRoom = currentRoom.getNorthExit();
                }
                if (direction.equals("east")) {
                        nextRoom = currentRoom.getEastExit();
                }
                if (direction.equals("south")) {
                        nextRoom = currentRoom.getSouthExit();
                }
                if (direction.equals("west")) {
                        nextRoom = currentRoom.getWestExit();
                }

                if (nextRoom == null) {
                        System.out.println("There is no door!");
                } // !Criar um menu para o usuário escolher os rolês
                else {
                        currentRoom = nextRoom;
                        System.out.println("Você chegou na região " + currentRoom.getNome()
                                        + "! \nSegue a lista de rolês, Para entrar em um rolê desta região, digite \"go numero\".");
                        System.out.println("");
                        for (int i = 0; i < currentRoom.getListaRoles().size(); i++) {
                                System.out.println(i + " - " + currentRoom.getListaRoles().get(i).getNome());

                        }

                        System.out.print("Exits: ");
                        if (currentRoom.getNorthExit() != null) {
                                System.out.print("north ");
                        }
                        if (currentRoom.getEastExit() != null) {
                                System.out.print("east ");
                        }
                        if (currentRoom.getSouthExit() != null) {
                                System.out.print("south ");
                        }
                        if (currentRoom.getWestExit() != null) {
                                System.out.print("west ");
                        }
                        System.out.println();
                }
        }

        /**
         * "Quit" was entered. Check the rest of the command to see
         * whether we really quit the game.
         * 
         * @return true, if this command quits the game, false otherwise.
         */
        private boolean quit(Command command) {
                if (command.hasSecondWord()) {
                        System.out.println("Quit what?");
                        return false;
                } else {
                        return true; // signal that we want to quit
                }
        }

        // *Cria todas as regiões, linka suas saidas e sorteia a regiao atual.
        private void createRegioes() {

                // *Cria cada região
                barreiro = new Regiao("Barreiro");
                centro = new Regiao("Centro");
                oeste = new Regiao("Oeste");
                pampulha = new Regiao("Pampulha");
                vendaNova = new Regiao("Venda Nova");
                leste = new Regiao("Leste");

                // *Seta as saidas de cada região
                barreiro.setExits(oeste, null, null, null);
                centro.setExits(pampulha, oeste, null, leste);
                oeste.setExits(null, null, barreiro, centro);
                pampulha.setExits(vendaNova, null, centro, null);
                vendaNova.setExits(null, null, centro, null);
                leste.setExits(null, centro, null, null);

                // *Seta os rolês de cada região
                // * Setando os rolês do barreiro:
                barreiro.setRoles(parqueJacques);
                barreiro.setRoles(shoppingDelRey);
                barreiro.setRoles(barDoGanso);
                // *Setando os rolês do centro
                centro.setRoles(lab);
                centro.setRoles(mercadoCentral);
                centro.setRoles(ccbb);
                // *Setando os rolês da oeste
                oeste.setRoles(miranteMangabeiras);
                oeste.setRoles(pracaDoPapa);
                oeste.setRoles(chalezinho);
                // *Setando os rolês de venda nova
                vendaNova.setRoles(raveParty);
                vendaNova.setRoles(shoppingEstacao);
                vendaNova.setRoles(estacaoMoveVilarinho);
                // *Setando os rolês da leste
                leste.setRoles(parqueMinicipalFazendaLagoaDoNado);
                leste.setRoles(barMuseuClubeDaEsquina);
                leste.setRoles(sescPalladium);
                // *Setando os rolês da pampulha
                pampulha.setRoles(igrejinhaDaPampulha);
                pampulha.setRoles(calouradaUFMG);
                pampulha.setRoles(zoologicoBeloHorizonte);

                Regiao[] listaRegioes = { barreiro,
                                centro,
                                oeste,
                                vendaNova,
                                leste,
                                pampulha };

                Random randomRegiao = new Random();
                currentRoom = listaRegioes[randomRegiao.nextInt(listaRegioes.length)];
                System.out.println("\n\n******************************");
                System.out.println("A região inicial sorteada foi:" + currentRoom.getNome());
                System.out.println("\n\n******************************");
        }

        private void createRoles() {
                // !Criando os rolês por região
                // Barreiro:
                parqueJacques = new Roles("Parque Jacques Cousteau",
                                "Este parque oferece uma área verde ampla e agradável, ótima para um encontro ao ar livre. Possui trilhas para caminhada, áreas de piquenique e lagos, proporcionando um ambiente tranquilo e relaxante.",
                                boneViseiraEsportiva);
                shoppingDelRey = new Roles("Shopping Del Rey",
                                "Um dos maiores shoppings de Belo Horizonte, oferece uma variedade de lojas, cinemas, restaurantes e áreas de entretenimento. É um local movimentado e ideal para quem gosta de fazer compras ou curtir um cinema com o date.\n",
                                livroAutografado);

                barDoGanso = new Roles("Shopping Del Rey",
                                "É um lugar simples para quem gosta de um clima mais intimista e quer desfrutar de um bom papo acompanhado de petiscos e bebidas.",
                                garrafaRoyalSalute);
                // Centro:
                lab = new Roles("LAB",
                                "Casa de show noturna com shows e música ao vivo, com uma atmosfera colorida, vibrante e alternativa, mesas na cobertura, e ótimos coquetéis",
                                copoPersonalizado);
                mercadoCentral = new Roles("Mercado Central",
                                "Um lugar icônico em BH, repleto de barracas com produtos regionais, especiarias, artesanatos e uma grande variedade de comidas típicas. Ideal para explorar a cultura local e saborear quitutes mineiros.",
                                discoDeVinil);
                ccbb = new Roles("CCBB - Centro Cultural Banco do Brasil",
                                "Um centro cultural com exposições de arte, cinema, teatro e diversas atividades culturais. Oferece uma programação diversificada e interessante para quem busca uma experiência cultural diferenciada.",
                                quadroDecorativo);
                // Oeste:
                miranteMangabeiras = new Roles("Mirante do Mangabeiras",
                                "Um dos pontos mais altos da cidade, oferece uma vista panorâmica espetacular de Belo Horizonte. Perfeito para apreciar o pôr do sol ou a cidade iluminada à noite, proporcionando um ambiente romântico",
                                chaveiro);
                pracaDoPapa = new Roles("Praça do Papa",
                                "Além de oferecer uma vista incrível da cidade, a praça é um local tranquilo e agradável para passear e relaxar. Ótimo para conversas ao ar livre e momentos mais descontraídos.",
                                lataRefrigerante);
                chalezinho = new Roles("Chalezinho",
                                "Casa noturna chique e espaçosa com lounges intimistas e contemporâneos, shows e gastronomia gourmet",
                                buqueDeFlor);
                // Venda nova:
                raveParty = new Roles("Rave Party:",
                                "Uma opção incrível para quem gosta de música eletrônica. Com multidões de jovens reunidos, a rave party te oferece uma incrível trilha sonora, repleta de músicas eletrônicas. Ideal para um encontro mais próximo à natureza.",
                                grudeTattoo);
                shoppingEstacao = new Roles("Shopping Estação",
                                "Um shopping com uma variedade de lojas, cinemas, restaurantes e áreas de lazer. Ideal para quem prefere um encontro mais casual, podendo desfrutar de diversas opções de entretenimento.",
                                camisaDeTime);
                estacaoMoveVilarinho = new Roles("Estação do Move Vilarinho",
                                "Uma estação de transporte público que se transformou em um ponto de encontro e lazer para os moradores locais. Oferece uma área com bancos, espaço verde e comércio ao redor.",
                                ingressosBalada);
                // Leste:
                parqueMinicipalFazendaLagoaDoNado = new Roles("Parque Municipal Fazenda Lagoa do Nado",
                                "Um parque com trilhas, lagos, quadras esportivas e áreas para piquenique. Ótimo para um encontro ao ar livre e para desfrutar de momentos relaxantes em meio à natureza.",
                                pinBroche);
                barMuseuClubeDaEsquina = new Roles("Bar e Museu Clube da Esquina",
                                "Um barzinho acolhedor e descontraído, com um ambiente amigável e boa música ao vivo, Chope e petiscos, além de CDs e livros que registram um movimento musical de Minas Gerais na década de 60.",
                                filtroDosSonhos);
                sescPalladium = new Roles("Sesc Paladium",
                                " Um espaço cultural e de entretenimento com programação diversificada, que inclui exposições, teatro, música e cinema. Ótima opção para um encontro cultural e relaxante.",
                                incenso);
                // Pampulha:
                igrejinhaDaPampulha = new Roles("Igreja São Francisco de Assis (Igrejinha da Pampulha)",
                                "Conhecida por sua arquitetura moderna de Oscar Niemeyer, é um local culturalmente significativo. Além da igreja, há também o Museu de Arte da Pampulha (MAP) nas proximidades, perfeito para um passeio cultural.",
                                terco);
                calouradaUFMG = new Roles("Calourada da UFMG",
                                "Um evento jovial, festivo e alegre, realizado em universidades para dar as boas-vindas aos novos estudantes, os calouros. É uma celebração marcada pela integração entre os alunos que estão ingressando na instituição de ensino superior.",
                                ingressosOpenBar);
                zoologicoBeloHorizonte = new Roles("Zoológico de Belo Horizonte",
                                "Ótima opção para quem gosta de animais e natureza. O zoológico possui uma variedade de espécies e oferece uma experiência educativa e divertida para um encontro descontraído.",
                                bichoPelucia);
        }

        private void createDates() {
                Date Maria = new Date("Maria",
                                "Maria é uma amante de raves e festivais de música eletrônica. Ela adora dançar e está sempre procurando novos eventos desse tipo.",
                                ingressosOpenBar, raveParty);

                Date Lucas = new Date("Lucas",
                                "Lucas é um estudante da UFMG e adora festas universitárias. Ele é extrovertido, gosta de socializar e de participar de eventos estudantis animados.\n",
                                copoStanley, calouradaUFMG);

                Date Sofia = new Date("Sofia",
                                "Sofia adora rolês chiques e tem paixão por carros. Ela gosta de lugares sofisticados e aprecia passeios de carro pela cidade.",
                                garrafaRoyalSalute, chalezinho);

                Date Ana = new Date("Ana",
                                "Ana é fã de barzinhos com clima descontraído e adora música popular brasileira (MPB). Ela gosta de ambientes aconchegantes para aproveitar a noite.",
                                discoDeVinil, barMuseuClubeDaEsquina);

                Date Joao = new Date("João",
                                "João é apaixonado por baladas e rolês alternativos. Ele gosta de explorar diferentes locais noturnos e sempre procura novas experiências.",
                                ingressosBalada, lab);

                Date Pedro = new Date("Pedro",
                                "Pedro é um estudante universitário que adora filosofia. Ele prefere locais calmos para reflexão, mas também aprecia ambientes alternativos e descolados.",
                                livroAutografado, ccbb);

                Date Rafael = new Date("Rafael",
                                "Rafael é um entusiasta de atividades esportivas e aventuras ao ar livre. Com sua energia contagiante, ele busca constantemente novas experiências esportivas e adrenalina.",
                                boneViseiraEsportiva, miranteMangabeiras);

        }

        // *Cria todos os presentes disponíveis
        private void createPresentes() {
                bichoPelucia = new Presente("Girafa de Pelúcia",
                                "Encante-se com a fofura desta adorável girafa de pelúcia. Perfeita para abraçar e decorar qualquer ambiente, esta pelúcia traz consigo a doçura e o encanto que só um presente especial pode oferecer.");

                terco = new Presente("Terço",
                                "Encontre paz e espiritualidade com este belo terço, uma manifestação de fé e devoção. Cada conta representa uma prece, guiando-o por um caminho de serenidade e conexão espiritual.");

                incenso = new Presente(
                                "Incenso de Lavanda",
                                "Desperte seus sentidos com o aroma relaxante da lavanda. Este incenso é uma ode à tranquilidade, proporcionando uma atmosfera calma e serena para momentos de meditação e relaxamento.");

                pinBroche = new Presente("Pin Broche Personalizado do Parque",
                                "Leve consigo uma lembrança única do nosso parque! Este pin personalizado é um símbolo especial que representa momentos divertidos e memoráveis, oferecendo um toque especial a qualquer coleção.");

                camisaDeTime = new Presente("Camisa de Time",
                                "Mostre seu amor pelo esporte e pelo seu time favorito com esta camisa oficial. Além de expressar sua paixão, esta peça é um símbolo de união e lealdade à equipe que você tanto admira.");

                grudeTattoo = new Presente("Grude Tattoo",
                                "Experimente a arte da tatuagem temporária com o Grude Tattoo! Esta tatuagem é uma expressão temporária de estilo e personalidade, permitindo que você exiba designs únicos por um tempo antes de desaparecer suavemente.");

                buqueDeFlor = new Presente("Buquê de Flores",
                                "Celebre momentos especiais com a beleza e fragrância deste encantador buquê de flores. Cada flor é um símbolo de carinho e afeto, transmitindo emoções que vão além das palavras.");

                lataRefrigerante = new Presente("Lata de Refrigerante",
                                "Refresque-se com esta lata de refrigerante, perfeita para momentos de descontração e diversão. Uma bebida gelada para acompanhar os melhores momentos da vida.");

                chaveiro = new Presente("Chaveiro Personalizado do Mirante",
                                "Carregue consigo a lembrança de vistas panorâmicas deslumbrantes com este chaveiro personalizado do nosso mirante. Este objeto é mais do que um acessório; é uma conexão com momentos memoráveis e paisagens deslumbrantes.");

                quadroDecorativo = new Presente("Quadro Decorativo",
                                "Transforme qualquer espaço com a beleza deste quadro decorativo. Cada detalhe da obra de arte é uma expressão de criatividade e estilo, adicionando personalidade e charme a qualquer ambiente.");

                filtroDosSonhos = new Presente("Filtro dos Sonhos",
                                "Deixe-se envolver pela magia e pelo simbolismo deste belo filtro dos sonhos. Originário das tradições indígenas, este artefato é mais do que um adorno decorativo; é um guardião dos sonhos.");

                copoPersonalizado = new Presente("Copo Personalizado",
                                "Desfrute de suas bebidas favoritas com estilo usando este copo personalizado. Cada detalhe deste copo foi pensado para proporcionar uma experiência única de degustação, unindo elegância e praticidade.");

                ingressosOpenBar = new Presente("Ingressos Open Bar para um festival de música eletrônica",
                                "Preparado para uma experiência única e eletrizante? Junte-se a nós no Festival Épico de Música Eletrônica, onde a batida envolvente e os ritmos pulsantes ganham vida em um espetáculo inigualável!");

                copoStanley = new Presente("Copo Stanley",
                                "Apresentamos o Copo Stanley, uma obra-prima de engenharia que transcende a mera funcionalidade, elevando o ato de saborear sua bebida a um novo patamar. Combinando estilo clássico e desempenho excepcional, este copo é mais do que um recipiente; é uma afirmação de qualidade e bom gosto.");

                garrafaRoyalSalute = new Presente("Uma garrafa de Royal Salute.",
                                "Descubra a realeza no mundo dos whiskies com a lendária garrafa de Royal Salute. Esta obra-prima destilada é um tributo à tradição, elegância e maestria na arte do whisky escocês. Cada gole é uma jornada sensorial que transcende o comum, levando você a um reino de sofisticação.");

                ingressosBalada = new Presente(" Ingressos para uma balada na cidade",
                                "Prepare-se para uma experiência única e eletrizante! Apresentamos os ingressos para a Balada City Vibes, uma noite que promete ser o ápice da diversão e boa música na cidade. Garanta seu lugar para uma celebração épica que ficará gravada na memória!");

                discoDeVinil = new Presente("Um disco de vinil de MPB.",
                                "Mergulhe na riqueza sonora da Música Popular Brasileira com este autêntico disco de vinil. Mais do que uma simples coleção de músicas, este vinil é uma jornada emocional pelas melodias cativantes e poesias profundas que definem a essência da música brasileira.");

                livroAutografado = new Presente("Um livro autografado por Taylor Jenkins Reid.",
                                "Se você é apaixonado por literatura, não pode perder a oportunidade de possuir uma jóia literária única. Apresentamos a edição especial autografada de Os sete maridos de Evelyn Hugo  pela renomada autora Taylor Jenkins Reid.");

                boneViseiraEsportiva = new Presente("Boné ou Viseira Esportiva.",
                                "Explore o mundo esportivo com um toque de estilo e proteção solar. Apresentamos a escolha entre o clássico Boné e a elegante Viseira Esportiva, ambos desenhados para elevar o seu visual durante suas atividades ao ar livre.");

        }

}
