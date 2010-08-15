/*
 * This file is part of Apparat.
 *
 * Apparat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Apparat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Apparat. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2010 Joa Ebert
 * http://www.joa-ebert.com/
 *
 */
package apparat.tools.asmifier

import apparat.tools.{ApparatConfiguration, ApparatConfigurationFactory}

import java.io.{File => JFile}

/**
 * @author Joa Ebert
 */
object ASMifierConfigurationFactory extends ApparatConfigurationFactory[ASMifierConfiguration] {
	override def fromConfiguration(config: ApparatConfiguration): ASMifierConfiguration = {
		val input = config("-i") map { path => new JFile(path) } getOrElse error("Input is required.")
		val output = config("-o") map { path => new JFile(path) }

		if(!input.exists) {
			error("Input "+input+" does not exist.")
		}

		if(output.isDefined) {
			val outputDir = output.get
			if(outputDir.exists) {
				if(!outputDir.isDirectory) {
					error("Output must point to a directory.")
				}
			} else {
				output.get.mkdirs()
			}
		}

		new ASMifierConfigurationImpl(input, output)
	}
}