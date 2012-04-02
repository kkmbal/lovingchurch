package lc.common.post.service;

import java.util.List;

import lc.common.post.domain.Post;

public interface PostMapper {
	public List<Post> listZip(Post post) throws Exception;
}
