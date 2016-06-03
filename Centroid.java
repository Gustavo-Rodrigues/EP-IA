import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;
import java.util.Arrays;
//11 4 28 8 18 9 22 23 3 custo 300
class Centroid{

    private ArrayList<Node> nodes;
    private int maxCapacity;

    public Centroid(Node nodes[],int maxCapacity){
        this.nodes = new ArrayList<Node>(Arrays.asList(nodes));
        this.maxCapacity = maxCapacity;
    }

    public Set construction(){
        //the cluster of clusters
        Set centroid = new Set();
        //copy of the nodes
        ArrayList<Node> cNodes = new ArrayList<Node>(nodes);
        cNodes.remove(0);

        while(cNodes.size()>0){
            //the cluster
            Route cluster = new Route();
            //every cluster starts with the farthest node
            Node farthest;
            //temp to calculate the farthest node
            int temp = 0;
            int pos = 0;
            for(int i = 0; i<cNodes.size(); i++){
                if(cNodes.get(i).distanceTo(0)>temp){
                    temp = cNodes.get(i).distanceTo(0);
                    pos = i;
                }
            }
            farthest = cNodes.get(pos);
            cluster.addNode(farthest);
            cNodes.remove(pos);

            boolean exceed = false;
            while(!exceed && cNodes.size()>0){
                //get the nearest node to the geometric center
                Node geometricCenter = cluster.getGeometricalCenter();
                temp = Integer.MAX_VALUE;
                pos = 0;
                for(int i = 0; i<cNodes.size(); i++){
                    if(cNodes.get(i).getDistance(geometricCenter)<temp){
                        temp = cNodes.get(i).getDistance(geometricCenter);
                        pos = i;
                    }
                }
                Node nearest = cNodes.get(pos);
                //add it to the cluster
                if(cluster.getCapacity()+ nearest.getDemand()<=maxCapacity){
                    cluster.addNode(nearest);
                    cluster.updateGeometricalCenter();
                    cNodes.remove(pos);
                }else{
                    exceed = true;
                }
            }
            centroid.addRoute(cluster);
        }
        /*
        for(int i = 0; i<centroid.setSize(); i++){
            Route newRoute = centroid.getRoute(i);
            newRoute.addNode(0,nodes.get(0));
            newRoute.updateGeometricalCenter();
            centroid.setRoute(i,newRoute);
        }
        */
        System.out.println("CONSTRUCTION");
        System.out.println(centroid);
        System.out.println(centroid.getDistance());
        return centroid;
    }

    public Set adjustment(Set centroid){
        for(int i = 0; i<centroid.setSize(); i++){
            //we might have a problem here
            for(int k = 0; k<centroid.getRoute(i).routeSize(); k++){
                for(int j = 0; j<centroid.setSize(); j++){
                    //distance to the geometrical center of cluster li, which is the current cluster
                    int disToLi = centroid.getRoute(i).getNode(k).getDistance(centroid.getRoute(i).getGeometricalCenter());
                    //distance to the geometrical center of cluster lj, which is the distance to another cluster
                    int disToLj = centroid.getRoute(i).getNode(k).getDistance(centroid.getRoute(j).getGeometricalCenter());

                    //if the difference to the new cluster is smaller than the current we move it if possible
                    if(i != j && disToLj < disToLi){
                        //if does not surpass the capacity restraint
                        if(centroid.getRoute(j).getCapacity() + centroid.getRoute(i).getNode(k).getDemand() < maxCapacity){
                            Route newClusterI = centroid.getRoute(i);
                            Route newClusterJ = centroid.getRoute(j);
                            newClusterI.removeNode(k);
                            newClusterJ.addNode(centroid.getRoute(i).getNode(k));
                            newClusterI.updateGeometricalCenter();
                            newClusterJ.updateGeometricalCenter();
                            centroid.setRoute(i,newClusterI);
                            centroid.setRoute(j,newClusterJ);
                            if(k>0) k--;
                        }
                    }
                }
            }
        }
        System.out.println("ADJUSTMENT");
        System.out.println(centroid);
        System.out.println(centroid.getDistance());
        return centroid;
    }

    /*
    public Route twoOpt(Route r){
        // Get tour size
        Route route = r.copyRoute();
        Route best = route.copyRoute();
        Route temp = route.copyRoute();
        int size = r.routeSize();
        int minI = -1;
        int minJ = -1;
        int minChange = 0;
        for ( int i = 1; i < size - 2; i++ ){
            for ( int j = i + 1; j< size-1; j++) {
                // route 1: dist(i,j) + dist(i+1,j+1)
                // route 2: dist(i,i+1) - dist(j,j+1)
                int distance1 = r.getNode(i).distanceTo(r.getNode(j).getId()-1) + r.getNode(i+1).distanceTo(r.getNode(j+1).getId()-1);
                int distance2 = r.getNode(i).distanceTo(r.getNode(i+1).getId()-1) + r.getNode(j).distanceTo(r.getNode(j+1).getId()-1);

                int better = distance1 - distance2;
                if ( better < minChange){
                    minChange = better;
                    minI = i;
                    minJ = j;
                    temp = route.copyRoute();
                    if(minI != -1 && minJ != -1){
                        temp.addNode(minI+1,r.getNode(minJ));
                        temp.addNode(minI+1+1,r.getNode(minJ+1));
                        temp.removeNode(minJ+2);
                        temp.removeNode(minJ+2);
                    }
                    if(temp.getDistance()<best.getDistance()) best = temp.copyRoute();
                }
            }
        }
        if(minI != -1 && minJ != -1){
            route.addNode(minI+1,r.getNode(minJ));
            route.addNode(minI+1+1,r.getNode(minJ+1));
            route.removeNode(minJ+2);
            route.removeNode(minJ+2);
        }
        if(temp.getDistance()<best.getDistance()) best = temp.copyRoute();
        return best;
    }*/
    public Route twoOpt(Route r){
        // Get tour size
        Route route = r.copyRoute();
        int size = r.routeSize();
        int minI = -1;
        int minJ = -1;
        int minChange = 0;
        for ( int i = 1; i < size - 3; i++ ){
            for ( int j = i + 2; j< size-1; j++) {
                // route 1: dist(i,j) + dist(i+1,j+1)
                // route 2: dist(i,i+1) - dist(j,j+1)
                int distance1 = r.getNode(i).distanceTo(r.getNode(j).getId()-1) + r.getNode(i+1).distanceTo(r.getNode(j+1).getId()-1);
                int distance2 = r.getNode(i).distanceTo(r.getNode(i+1).getId()-1) + r.getNode(j).distanceTo(r.getNode(j+1).getId()-1);

                int better = distance1 - distance2;
                if ( better < minChange){
                    minChange = better;
                    minI = i;
                    minJ = j;
                }
            }
        }
        if(minI != -1 && minJ != -1){
            route.addNode(minI+1,r.getNode(minJ));
            route.addNode(minI+1+1,r.getNode(minJ+1));
            route.removeNode(minJ+2);
            route.removeNode(minJ+2);
        }
        return route;
    }

    public void algorithm(){

        //Cluster Construction Phase
        Set clusters = construction();
        Set best = clusters.copySet();

        //Add depot to the clusters
        for(int i = 0; i<clusters.setSize(); i++){
            Route newRoute = clusters.getRoute(i);
            newRoute.addNode(0,nodes.get(0));
            clusters.setRoute(i,newRoute);
        }
        //Get result by applying TSP on clusters
        for(int i = 0; i<clusters.setSize(); i++){
            Route newRoute = clusters.getRoute(i);
            newRoute = twoOpt(newRoute);
            clusters.setRoute(i,newRoute);
        }

        //Remove depot from the clusters
        for(int i = 0; i<clusters.setSize(); i++){
            Route newRoute = clusters.getRoute(i);
            newRoute.removeNode(0);
            clusters.setRoute(i,newRoute);
        }

        if(clusters.getDistance()< best.getDistance()) best = clusters.copySet();
        System.out.println("BEFORE");
        System.out.println(clusters);
        do{
            if( clusters.equals(adjustment(clusters)) ) break;
            else{
                //Add depot to the clusters
                for(int i = 0; i<clusters.setSize(); i++){
                    Route newRoute = clusters.getRoute(i);
                    newRoute.addNode(0,nodes.get(0));
                    clusters.setRoute(i,newRoute);
                }

                //Get result by applying TSP on clusters
                for(int i = 0; i<clusters.setSize(); i++){
                    Route newRoute = clusters.getRoute(i);
                    newRoute = twoOpt(newRoute);
                    clusters.setRoute(i,newRoute);
                }

                //Remove depot from the clusters
                for(int i = 0; i<clusters.setSize(); i++){
                    Route newRoute = clusters.getRoute(i);
                    newRoute.removeNode(0);
                    clusters.setRoute(i,newRoute);
                }
                if(clusters.getDistance()< best.getDistance()) best = clusters.copySet();
            }
        }while(true);

        System.out.println("ALGORITHM");
        for(int i = 0; i<best.setSize(); i++){
            Route newRoute = best.getRoute(i);
            newRoute.addNode(0,nodes.get(0));
            best.setRoute(i,newRoute);
        }

        System.out.println(best);
        System.out.println(best.getDistance());

    }
}
