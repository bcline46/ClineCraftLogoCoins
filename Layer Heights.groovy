import eu.mihosoft.vrl.v3d.svg.*;

import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine

import eu.mihosoft.vrl.v3d.CSG
import eu.mihosoft.vrl.v3d.Extrude;
import eu.mihosoft.vrl.v3d.Polygon

File f = ScriptingEngine
	.fileFromGit(
		"https://github.com/bcline46/ClineCraftLogoCoins.git",//git repo URL
		"master",//branch
		"ClineCraftLogoCoins.SVG"// File from within the Git repo
	)
println "Extruding SVG "+f.getAbsolutePath()
SVGLoad s = new SVGLoad(f.toURI())
println "Layers= "+s.getLayers()
// A map of layers to polygons
HashMap<String,List<Polygon>> polygonsByLayer = s.toPolygons()
// extrude all layers to a map to 10mm thick
HashMap<String,ArrayList<CSG>> csgByLayers = s.extrudeLayers(10)
// extrude just one layer to 10mm
def holeParts = s.extrudeLayerToCSG(10,"1-Holes")
.movez(3)
// seperate holes and outsides using layers to differentiate
def outsideParts = s.extrudeLayerToCSG(4,"2-Letters")
					.difference(holeParts)
// layers can be extruded at different depths					
def flag = s.extrudeLayerToCSG(4.5,"3-Flag")
					.movez(3)
def FlagParts1 = s.extrudeLayerToCSG(2,"3.5-FlagOutside")
					.movez(3)
def BladeParts1 = s.extrudeLayerToCSG(4,"4-Blade")
				.difference(holeParts)
				.difference(FlagParts1)
				.difference(flag)


return [CSG.unionAll([outsideParts,BladeParts1])]