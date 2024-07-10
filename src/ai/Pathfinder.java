package ai;

import main.GamePanel;

import java.util.ArrayList;

public class Pathfinder { // USING A* ALGORITHM

    GamePanel gp;
    Node [][] node;
    ArrayList <Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }
    public void instantiateNodes() {

        node = new Node[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        int col = 0;
        int row = 0;

        while(col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            node[col][row] = new Node(col,row);

            col++;
            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {

        int col = 0;
        int row = 0;

        while(col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

        // RESET OTHER SETTINGS
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes (int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        // SET START AND GOAL NODE
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            // SET SOLID NODE
            // CHECK TILES
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision) {
                node[col][row].solid = true;
            }

            // CHECK INTRACTABLES
            for(int i = 0; i < gp.iTile[1].length; i++) {
                if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible) {
                    int itCol = gp.iTile[gp.currentMap][i].worldX/gp.TILE_SIZE;
                    int itRow = gp.iTile[gp.currentMap][i].worldY/gp.TILE_SIZE;
                    node[itCol][itRow].solid = true;
                }
            }

            // SET COST
            getCost(node[col][row]);

            col++;
            if(col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node){

        // G COST
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F COST
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while(!goalReached && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            // CHECK CURRENT NODE
            currentNode.checked = true;
            openList.remove(currentNode);

            // OPEN UP TOP NODE
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }

            // OPEN LEFT NODE
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }

            // OPEN DOWN NODE
            if(row + 1 < gp.MAX_WORLD_ROW) {
                openNode(node[col][row+1]);
            }

            // OPEN RIGHT NODE
            if(col + 1 < gp.MAX_WORLD_COL) {
                openNode(node[col+1][row]);
            }

            // FIND THE BEST NODE
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i < openList.size(); i++){

                // CHECK IF FCOST IS BETTER
                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }

                // CHECK IF F COST EQUAL, CHECK GCOST
                else if (openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // IF NO NODE IN THE OPEN LIST
            if(openList.isEmpty()){
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
