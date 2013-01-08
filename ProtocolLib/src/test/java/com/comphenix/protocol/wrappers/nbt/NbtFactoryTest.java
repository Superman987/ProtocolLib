/*
 *  ProtocolLib - Bukkit server library that allows access to the Minecraft protocol.
 *  Copyright (C) 2012 Kristian S. Stangeland
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the 
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of 
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program; 
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 *  02111-1307 USA
 */

package com.comphenix.protocol.wrappers.nbt;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import com.comphenix.protocol.utility.MinecraftReflection;

public class NbtFactoryTest {
	@BeforeClass
	public static void initializeBukkit() {
		// Initialize reflection
		MinecraftReflection.setMinecraftPackage("net.minecraft.server.v1_4_6", "org.bukkit.craftbukkit.v1_4_6");
	}
	
	@Test
	public void testFromStream() {
		WrappedCompound compound = WrappedCompound.fromName("tag");
		
		compound.put("name", "Test Testerson");
		compound.put("age", 42);
		
		compound.put(NbtFactory.ofList("nicknames", "a", "b", "c"));

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		DataOutput test = new DataOutputStream(buffer);
		compound.write(test);

		ByteArrayInputStream source = new ByteArrayInputStream(buffer.toByteArray());
		DataInput input = new DataInputStream(source);
		
		NbtCompound cloned = (NbtCompound) NbtFactory.fromStream(input);
		
		assertEquals(compound.getString("name"), cloned.getString("name"));
		assertEquals(compound.getInteger("age"), cloned.getInteger("age"));
		assertEquals(compound.getList("nicknames"), cloned.getList("nicknames"));
	}
}