package org.lumongo.server.rest;

import org.apache.log4j.Logger;
import org.lumongo.LumongoConstants;
import org.lumongo.server.index.LumongoIndexManager;
import org.lumongo.util.StreamHelper;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Path(LumongoConstants.ASSOCIATED_DOCUMENTS_URL)
public class AssociatedResource {

	private final static Logger log = Logger.getLogger(AssociatedResource.class);


	private LumongoIndexManager indexManager;

	public AssociatedResource(LumongoIndexManager indexManager) {
		this.indexManager = indexManager;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response get(@Context Response response, @QueryParam(LumongoConstants.ID) final String uniqueId,
					@QueryParam(LumongoConstants.FILE_NAME) final String fileName, @QueryParam(LumongoConstants.INDEX) final String indexName) {

		StreamingOutput stream = new StreamingOutput() {

			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				if (uniqueId != null && fileName != null && indexName != null) {
					InputStream is = indexManager.getAssociatedDocumentStream(indexName, uniqueId, fileName);
					if (is != null) {
						StreamHelper.copyStream(is, output);

					}
					else {
						throw new WebApplicationException("Cannot find associated document with uniqueId <" + uniqueId + "> with fileName <" + fileName + ">",
										LumongoConstants.NOT_FOUND);
					}
				}
				else {
					throw new WebApplicationException(LumongoConstants.ID + " and " + LumongoConstants.FILE_NAME + " are required",
									LumongoConstants.BAD_REQUEST);
				}
			}

		};

		return Response.ok(stream).header("content-disposition", "attachment; filename = " + fileName).build();

	}

	@POST
	@Produces({ MediaType.TEXT_XML })
	public Response post(@QueryParam(LumongoConstants.ID) String uniqueId, @QueryParam(LumongoConstants.FILE_NAME) String fileName,
					@QueryParam(LumongoConstants.INDEX) String indexName, @QueryParam(LumongoConstants.COMPRESSED) Boolean compressed, InputStream is) {
		if (uniqueId != null && fileName != null && indexName != null) {

			try {

				if (compressed == null) {
					compressed = false;
				}

				indexManager.storeAssociatedDocument(indexName, uniqueId, fileName, is, compressed, null);

				return Response.status(LumongoConstants.SUCCESS)
								.entity("Stored associated document with uniqueId <" + uniqueId + "> and fileName <" + fileName + ">").build();
			}
			catch (Exception e) {
				log.error(e.getClass().getSimpleName() + ": ", e);
				return Response.status(LumongoConstants.INTERNAL_ERROR).entity(e.getMessage()).build();
			}
		}
		else {
			throw new WebApplicationException(LumongoConstants.ID + " and " + LumongoConstants.FILE_NAME + " are required",
							LumongoConstants.BAD_REQUEST);
		}

	}
}